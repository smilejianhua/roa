/** 
 * Copyright (c) 2012-2015 Wonders Information Co.,Ltd. All Rights Reserved.
 * 5/F 1600 Nanjing RD(W), Shanghai 200040, P.R.C 
 *
 * This software is the confidential and proprietary information of Wonders Group.
 * (Public Service Division / Research & Development Center). You shall not disclose such
 * Confidential Information and shall use it only in accordance with 
 * the terms of the license agreement you entered into with Wonders Group. 
 *
 * Distributable under GNU LGPL license by gun.org
 */
package com.wondersgroup.roa.context.impl;

import com.wondersgroup.roa.*;
import com.wondersgroup.roa.config.SystemParameterNames;
import com.wondersgroup.roa.context.ROAContext;
import com.wondersgroup.roa.context.ROAException;
import com.wondersgroup.roa.context.ROAMarshaller;
import com.wondersgroup.roa.context.ROARequestContext;
import com.wondersgroup.roa.context.RequestContextBuilder;
import com.wondersgroup.roa.context.ServiceMethodAdapter;
import com.wondersgroup.roa.context.ServiceMethodHandler;
import com.wondersgroup.roa.context.ServiceRouter;
import com.wondersgroup.roa.event.*;
import com.wondersgroup.roa.marshaller.JacksonJsonROAMarshaller;
import com.wondersgroup.roa.marshaller.JaxbXmlROAMarshaller;
import com.wondersgroup.roa.request.ROARequestMessageConverter;
import com.wondersgroup.roa.request.UploadFileConverter;
import com.wondersgroup.roa.response.ErrorResponse;
import com.wondersgroup.roa.response.RejectedServiceResponse;
import com.wondersgroup.roa.response.ServiceUnavailableErrorResponse;
import com.wondersgroup.roa.response.TimeoutErrorResponse;
import com.wondersgroup.roa.security.*;
import com.wondersgroup.roa.security.SecurityManager;
import com.wondersgroup.roa.security.impl.DefaultInvokeTimesController;
import com.wondersgroup.roa.security.impl.DefaultSecurityManager;
import com.wondersgroup.roa.session.DefaultSessionManager;
import com.wondersgroup.roa.session.SessionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.*;

public class AnnotationServletServiceRouter implements ServiceRouter {

	public static final String APPLICATION_XML = "application/xml";

	public static final String APPLICATION_JSON = "application/json";

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private static final String I18N_ROP_ERROR = "i18n/rop/error";

	private ServiceMethodAdapter serviceMethodAdapter = new AnnotationServiceMethodAdapter();

	private ROAMarshaller xmlMarshallerRop = new JaxbXmlROAMarshaller();

	private ROAMarshaller jsonMarshallerRop = new JacksonJsonROAMarshaller();

	private RequestContextBuilder requestContextBuilder;

	private SecurityManager securityManager;

	private FormattingConversionService formattingConversionService;

	private ThreadPoolExecutor threadPoolExecutor;

	private ROAContext ropContext;

	private ROAEventMulticaster ropEventMulticaster;

	private List<Interceptor> interceptors = new ArrayList<Interceptor>();

	private List<ROAEventListener> listeners = new ArrayList<ROAEventListener>();

	private boolean signEnable = true;

	private ApplicationContext applicationContext;

	// 所有服务方法的最大过期时间，单位为秒(0或负数代表不限制)
	private int serviceTimeoutSeconds = Integer.MAX_VALUE;

	// 会话管理器
	private SessionManager sessionManager = new DefaultSessionManager();

	// 服务调用频率管理器
	private InvokeTimesController invokeTimesController = new DefaultInvokeTimesController();

	private Class<? extends ThreadFerry> threadFerryClass;

	private String extErrorBasename = "i18n/rop/ropError";

	@Override
	public void service(Object request, Object response) {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;

		// 获取服务方法最大过期时间
		String method = servletRequest.getParameter(SystemParameterNames.getMethod());
		String version = servletRequest.getParameter(SystemParameterNames.getVersion());
		int serviceMethodTimeout = getServiceMethodTimeout(method, version);
		long beginTime = System.currentTimeMillis();

		// 使用异常方式调用服务方法
		try {

			// 执行线程摆渡
			ThreadFerry threadFerry = buildThreadFerryInstance();
			if (threadFerry != null) {
				threadFerry.doInSrcThread();
			}

			ServiceRunnable runnable = new ServiceRunnable(servletRequest, servletResponse, threadFerry);
			Future<?> future = this.threadPoolExecutor.submit(runnable);
			while (!future.isDone()) {
				future.get(serviceMethodTimeout, TimeUnit.SECONDS);
			}
		}
		catch (RejectedExecutionException ree) {// 超过最大的服务平台的最大资源限制，无法提供服务
			ROARequestContext ropRequestContext = buildRequestContextWhenException(servletRequest, beginTime);
			RejectedServiceResponse ropResponse = new RejectedServiceResponse(ropRequestContext.getLocale());
			writeResponse(ropResponse, servletResponse, ServletRequestContextBuilder.getResponseFormat(servletRequest));
			fireAfterDoServiceEvent(ropRequestContext);
		}
		catch (TimeoutException e) {// 服务时间超限
			ROARequestContext ropRequestContext = buildRequestContextWhenException(servletRequest, beginTime);
			TimeoutErrorResponse ropResponse = new TimeoutErrorResponse(ropRequestContext.getMethod(),
					ropRequestContext.getLocale(), serviceMethodTimeout);
			writeResponse(ropResponse, servletResponse, ServletRequestContextBuilder.getResponseFormat(servletRequest));
			fireAfterDoServiceEvent(ropRequestContext);
		}
		catch (Throwable throwable) {// 产生未知的错误
			ServiceUnavailableErrorResponse ropResponse = new ServiceUnavailableErrorResponse(method,
					ServletRequestContextBuilder.getLocale(servletRequest), throwable);
			writeResponse(ropResponse, servletResponse, ServletRequestContextBuilder.getResponseFormat(servletRequest));
			ROARequestContext ropRequestContext = buildRequestContextWhenException(servletRequest, beginTime);
			fireAfterDoServiceEvent(ropRequestContext);
		}
	}

	@Override
	public void startup() {
		if (logger.isInfoEnabled()) {
			logger.info("开始启动Rop框架...");
		}
		Assert.notNull(this.applicationContext, "Spring上下文不能为空");

		// 初始化类型转换器
		if (this.formattingConversionService == null) {
			this.formattingConversionService = getDefaultConversionService();
		}
		registerConverters(formattingConversionService);

		// 实例化ServletRequestContextBuilder
		this.requestContextBuilder = new ServletRequestContextBuilder(this.formattingConversionService,
				this.sessionManager);

		// 设置校验器
		if (this.securityManager == null) {
			this.securityManager = new DefaultSecurityManager();
		}

		// 设置异步执行器
		if (this.threadPoolExecutor == null) {
			this.threadPoolExecutor = new ThreadPoolExecutor(200, Integer.MAX_VALUE, 5 * 60, TimeUnit.SECONDS,
					new LinkedBlockingDeque<Runnable>());
		}

		// 创建Rop上下文
		this.ropContext = buildRopContext();

		// 初始化事件发布器
		this.ropEventMulticaster = buildRopEventMulticaster();

		// 初始化信息源
		initMessageSource();

		// 产生ROA框架初始化事件
		fireAfterStartedROAEvent();

		if (logger.isInfoEnabled()) {
			logger.info("Rop框架启动成功！");
		}
	}

	private void registerConverters(FormattingConversionService conversionService) {
		conversionService.addConverter(new ROARequestMessageConverter());
		conversionService.addConverter(new UploadFileConverter());
	}

	private ThreadFerry buildThreadFerryInstance() {
		if (threadFerryClass != null) {
			return BeanUtils.instantiate(threadFerryClass);
		}
		else {
			return null;
		}
	}

	@Override
	public void shutdown() {
		fireBeforeCloseRopEvent();
		threadPoolExecutor.shutdown();
	}

	@Override
	public void setSignEnable(boolean signEnable) {
		if (!signEnable && logger.isInfoEnabled()) {
			logger.info("关闭签名验证功能");
		}
		this.signEnable = signEnable;
	}

	@Override
	public void setThreadFerryClass(Class<? extends ThreadFerry> threadFerryClass) {
		this.threadFerryClass = threadFerryClass;
	}

	@Override
	public void setInvokeTimesController(InvokeTimesController invokeTimesController) {
		this.invokeTimesController = invokeTimesController;
	}

	@Override
	public void setServiceTimeoutSeconds(int serviceTimeoutSeconds) {
		this.serviceTimeoutSeconds = serviceTimeoutSeconds;
	}

	@Override
	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}

	@Override
	public void setFormattingConversionService(FormattingConversionService formatConversionService) {
		this.formattingConversionService = formatConversionService;
	}

	@Override
	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	/**
	 * 获取默认的格式化转换器
	 * 
	 * @return
	 */
	private FormattingConversionService getDefaultConversionService() {
		FormattingConversionServiceFactoryBean serviceFactoryBean = new FormattingConversionServiceFactoryBean();
		serviceFactoryBean.afterPropertiesSet();
		return serviceFactoryBean.getObject();
	}

	@Override
	public void setExtErrorBasename(String extErrorBasename) {
		this.extErrorBasename = extErrorBasename;
	}

	@Override
	public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
		this.threadPoolExecutor = threadPoolExecutor;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public ROAContext getROAContext() {
		return this.ropContext;
	}

	@Override
	public void addInterceptor(Interceptor interceptor) {
		this.interceptors.add(interceptor);
	}

	@Override
	public void addListener(ROAEventListener listener) {
		this.listeners.add(listener);
	}

	public int getServiceTimeoutSeconds() {
		return serviceTimeoutSeconds >= 0 ? serviceTimeoutSeconds : Integer.MAX_VALUE;
	}

	/**
	 * 取最小的过期时间
	 * 
	 * @param method
	 * @param version
	 * @return
	 */
	private int getServiceMethodTimeout(String method, String version) {
		ServiceMethodHandler serviceMethodHandler = ropContext.getServiceMethodHandler(method, version);
		if (serviceMethodHandler == null) {
			return getServiceTimeoutSeconds();
		}
		else {
			int methodTimeout = serviceMethodHandler.getServiceMethodDefinition().getTimeout();
			if (methodTimeout <= 0) {
				return getServiceTimeoutSeconds();
			}
			else {
				return methodTimeout;
			}
		}
	}

	private class ServiceRunnable implements Runnable {

		private HttpServletRequest servletRequest;
		private HttpServletResponse servletResponse;
		private ThreadFerry threadFerry;

		private ServiceRunnable(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
				ThreadFerry threadFerry) {
			this.servletRequest = servletRequest;
			this.servletResponse = servletResponse;
			this.threadFerry = threadFerry;
		}

		@Override
		public void run() {
			if (threadFerry != null) {
				threadFerry.doInDestThread();
			}

			ROARequestContext ropRequestContext = null;

			try {
				// 用系统级参数构造一个RequestContext实例（第一阶段绑定）
				ropRequestContext = requestContextBuilder.buildBySysParams(ropContext, servletRequest);

				// 验证系统级参数的合法性
				MainError mainError = securityManager.validateSystemParameters(ropRequestContext);
				if (mainError != null) {
					ropRequestContext.setROAResponse(new ErrorResponse(mainError));
				}
				else {

					// 绑定业务数据（第二阶段绑定）
					requestContextBuilder.bindBusinessParams(ropRequestContext);

					// 进行其它检查业务数据合法性，业务安全等
					mainError = securityManager.validateOther(ropRequestContext);
					if (mainError != null) {
						ropRequestContext.setROAResponse(new ErrorResponse(mainError));
					}
					else {
						firePreDoServiceEvent(ropRequestContext);

						// 服务处理前拦截
						invokeBeforceServiceOfInterceptors(ropRequestContext);

						if (ropRequestContext.getROAResponse() == null) { // 拦截器未生成response
							// 如果拦截器没有产生ropResponse时才调用服务方法
							ropRequestContext.setROAResponse(doService(ropRequestContext));

							// 输出响应前拦截
							invokeBeforceResponseOfInterceptors(ropRequestContext);
						}
					}
				}
				// 输出响应
				writeResponse(ropRequestContext.getROAResponse(), servletResponse, ropRequestContext.getMessageFormat());
			}
			catch (Throwable e) {
				String method = ropRequestContext.getMethod();
				Locale locale = ropRequestContext.getLocale();
				ServiceUnavailableErrorResponse ropResponse = new ServiceUnavailableErrorResponse(method, locale, e);

				// 输出响应前拦截
				invokeBeforceResponseOfInterceptors(ropRequestContext);
				writeResponse(ropResponse, servletResponse, ropRequestContext.getMessageFormat());
			}
			finally {
				if (ropRequestContext != null) {

					// 发布服务完成事件
					ropRequestContext.setServiceEndTime(System.currentTimeMillis());

					// 完成一次服务请求，计算次数
					invokeTimesController.caculateInvokeTimes(ropRequestContext.getApiKey(),
							ropRequestContext.getSession());
					fireAfterDoServiceEvent(ropRequestContext);
				}
			}
		}
	}

	/**
	 * 当发生异常时，创建一个请求上下文对象
	 * 
	 * @param request
	 * @param beginTime
	 * @return
	 */
	private ROARequestContext buildRequestContextWhenException(HttpServletRequest request, long beginTime) {
		ROARequestContext ropRequestContext = requestContextBuilder.buildBySysParams(ropContext, request);
		ropRequestContext.setServiceBeginTime(beginTime);
		ropRequestContext.setServiceEndTime(System.currentTimeMillis());
		return ropRequestContext;
	}

	private ROAContext buildRopContext() {
		DefaultROAContext defaultRopContext = new DefaultROAContext(this.applicationContext);
		defaultRopContext.setSignEnable(this.signEnable);
		defaultRopContext.setSessionManager(sessionManager);
		return defaultRopContext;
	}

	private ROAEventMulticaster buildRopEventMulticaster() {

		SimpleROAEventMulticaster simpleRopEventMulticaster = new SimpleROAEventMulticaster();

		// 设置异步执行器
		if (this.threadPoolExecutor != null) {
			simpleRopEventMulticaster.setExecutor(this.threadPoolExecutor);
		}

		// 添加事件监听器
		if (this.listeners != null && this.listeners.size() > 0) {
			for (ROAEventListener ropEventListener : this.listeners) {
				simpleRopEventMulticaster.addROAListener(ropEventListener);
			}
		}

		return simpleRopEventMulticaster;
	}

	/**
	 * 发布ROA启动后事件
	 */
	private void fireAfterStartedROAEvent() {
		AfterStartedROAEvent ropEvent = new AfterStartedROAEvent(this, this.ropContext);
		this.ropEventMulticaster.multicastEvent(ropEvent);
	}

	/**
	 * 发布Rop启动后事件
	 */
	private void fireBeforeCloseRopEvent() {
		PreCloseROAEvent ropEvent = new PreCloseROAEvent(this, this.ropContext);
		this.ropEventMulticaster.multicastEvent(ropEvent);
	}

	private void fireAfterDoServiceEvent(ROARequestContext ropRequestContext) {
		this.ropEventMulticaster.multicastEvent(new AfterDoServiceEvent(this, ropRequestContext));
	}

	private void firePreDoServiceEvent(ROARequestContext ropRequestContext) {
		this.ropEventMulticaster.multicastEvent(new PreDoServiceEvent(this, ropRequestContext));
	}

	/**
	 * 在服务调用之前拦截
	 * 
	 * @param ropRequestContext
	 */
	private void invokeBeforceServiceOfInterceptors(ROARequestContext ropRequestContext) {
		Interceptor tempInterceptor = null;
		try {
			if (interceptors != null && interceptors.size() > 0) {
				for (Interceptor interceptor : interceptors) {

					interceptor.beforeService(ropRequestContext);

					// 如果有一个产生了响应，则阻止后续的调用
					if (ropRequestContext.getROAResponse() != null) {
						if (logger.isDebugEnabled()) {
							logger.debug("拦截器[" + interceptor.getClass().getName() + "]产生了一个RopResponse,"
									+ " 阻止本次服务请求继续，服务将直接返回。");
						}
						return;
					}
				}
			}
		}
		catch (Throwable e) {
			ropRequestContext.setROAResponse(new ServiceUnavailableErrorResponse(ropRequestContext.getMethod(),
					ropRequestContext.getLocale(), e));
			logger.error("在执行拦截器[" + tempInterceptor.getClass().getName() + "]时发生异常.", e);
		}
	}

	/**
	 * 在服务调用之后，返回响应之前拦截
	 * 
	 * @param ropRequestContext
	 */
	private void invokeBeforceResponseOfInterceptors(ROARequestContext ropRequestContext) {
		Interceptor tempInterceptor = null;
		try {
			if (interceptors != null && interceptors.size() > 0) {
				for (Interceptor interceptor : interceptors) {
					interceptor.beforeResponse(ropRequestContext);
				}
			}
		}
		catch (Throwable e) {
			ropRequestContext.setROAResponse(new ServiceUnavailableErrorResponse(ropRequestContext.getMethod(),
					ropRequestContext.getLocale(), e));
			logger.error("在执行拦截器[" + tempInterceptor.getClass().getName() + "]时发生异常.", e);
		}
	}

	private void writeResponse(Object ropResponse, HttpServletResponse httpServletResponse, MessageFormat messageFormat) {
		try {
			httpServletResponse.setCharacterEncoding(Constants.UTF8);
			if (messageFormat == MessageFormat.xml) {
				httpServletResponse.setContentType(APPLICATION_XML);
				xmlMarshallerRop.marshaller(ropResponse, httpServletResponse.getOutputStream());
			}
			else {
				httpServletResponse.setContentType(APPLICATION_JSON);
				jsonMarshallerRop.marshaller(ropResponse, httpServletResponse.getOutputStream());
			}
		}
		catch (IOException e) {
			throw new ROAException(e);
		}
	}

	private Object doService(ROARequestContext ropRequestContext) {
		Object ropResponse = null;
		if (ropRequestContext.getMethod() == null) {
			ropResponse = new ErrorResponse(MainErrors.getError(MainErrorType.MISSING_METHOD,
					ropRequestContext.getLocale()));
		}
		else if (!ropContext.isValidMethod(ropRequestContext.getMethod())) {
			ropResponse = new ErrorResponse(MainErrors.getError(MainErrorType.INVALID_METHOD,
					ropRequestContext.getLocale()));
		}
		else {
			try {
				ropResponse = serviceMethodAdapter.invokeServiceMethod(ropRequestContext);
			}
			catch (Exception e) { // 出错则招聘服务不可用的异常
				if (logger.isInfoEnabled()) {
					logger.info("调用" + ropRequestContext.getMethod() + "时发生异常，异常信息为：" + e.getMessage());
					e.printStackTrace();
				}
				ropResponse = new ServiceUnavailableErrorResponse(ropRequestContext.getMethod(),
						ropRequestContext.getLocale(), e);
			}
		}
		return ropResponse;
	}

	/**
	 * 设置国际化资源信息
	 */
	private void initMessageSource() {
		if (logger.isInfoEnabled()) {
			logger.info("加载错误码国际化资源：" + I18N_ROP_ERROR + "," + this.extErrorBasename);
		}
		ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
		bundleMessageSource.setBasenames(I18N_ROP_ERROR, this.extErrorBasename);
		MessageSourceAccessor messageSourceAccessor = new MessageSourceAccessor(bundleMessageSource);
		MainErrors.setErrorMessageSourceAccessor(messageSourceAccessor);
		SubErrors.setErrorMessageSourceAccessor(messageSourceAccessor);
	}

	public SecurityManager getSecurityManager() {
		return securityManager;
	}

	public FormattingConversionService getFormattingConversionService() {
		return formattingConversionService;
	}

	public ThreadPoolExecutor getThreadPoolExecutor() {
		return threadPoolExecutor;
	}

	public ROAEventMulticaster getRopEventMulticaster() {
		return ropEventMulticaster;
	}

	public List<Interceptor> getInterceptors() {
		return interceptors;
	}

	public List<ROAEventListener> getListeners() {
		return listeners;
	}

	public boolean isSignEnable() {
		return signEnable;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public String getExtErrorBasename() {
		return extErrorBasename;
	}
}