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
import com.wondersgroup.roa.annotation.HttpAction;
import com.wondersgroup.roa.config.SystemParameterNames;
import com.wondersgroup.roa.context.ROAContext;
import com.wondersgroup.roa.context.ROARequest;
import com.wondersgroup.roa.context.ROARequestContext;
import com.wondersgroup.roa.context.RequestContextBuilder;
import com.wondersgroup.roa.context.ServiceMethodHandler;
import com.wondersgroup.roa.security.MainErrorType;
import com.wondersgroup.roa.security.MainErrors;
import com.wondersgroup.roa.session.SessionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.propertyeditors.LocaleEditor;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.ServletRequestDataBinder;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 构建{@link com.wondersgroup.roa.context.ROARequestContext}实例
 * 
 * @author Jacky.Li
 */
public class ServletRequestContextBuilder implements RequestContextBuilder {

	// 通过前端的负载均衡服务器时，请求对象中的IP会变成负载均衡服务器的IP，因此需要特殊处理，下同。
	public static final String X_REAL_IP = "X-Real-IP";

	public static final String X_FORWARDED_FOR = "X-Forwarded-For";

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private FormattingConversionService conversionService;

	private Validator validator;

	private SessionManager sessionManager;

	public ServletRequestContextBuilder(FormattingConversionService conversionService, SessionManager sessionManager) {
		this.conversionService = conversionService;
		this.sessionManager = sessionManager;
	}

	@Override
	public SimpleROARequestContext buildBySysParams(ROAContext roaContext, Object request) {
		if (!(request instanceof HttpServletRequest)) {
			throw new IllegalArgumentException("请求对象必须是HttpServletRequest的类型");
		}

		HttpServletRequest servletRequest = (HttpServletRequest) request;
		SimpleROARequestContext requestContext = new SimpleROARequestContext(roaContext);

		// 设置请求对象及参数列表
		requestContext.setRawRequestObject(servletRequest);
		requestContext.setAllParams(getRequestParams(servletRequest));
		requestContext.setIp(getRemoteAddr(servletRequest)); // 感谢melin所指出的BUG

		// 设置服务的系统级参数
		requestContext.setApiKey(servletRequest.getParameter(SystemParameterNames.getApiKey()));
		requestContext.setSessionId(servletRequest.getParameter(SystemParameterNames.getSessionId()));
		requestContext.setMethod(servletRequest.getParameter(SystemParameterNames.getMethod()));
		requestContext.setVersion(servletRequest.getParameter(SystemParameterNames.getVersion()));
		requestContext.setLocale(getLocale(servletRequest));
		requestContext.setFormat(getFormat(servletRequest));
		requestContext.setMessageFormat(getResponseFormat(servletRequest));
		requestContext.setSign(servletRequest.getParameter(SystemParameterNames.getSign()));
		requestContext.setHttpAction(HttpAction.fromValue(servletRequest.getMethod()));

		// 设置服务处理器
		ServiceMethodHandler serviceMethodHandler = roaContext.getServiceMethodHandler(requestContext.getMethod(),
				requestContext.getVersion());
		requestContext.setServiceMethodHandler(serviceMethodHandler);

		return requestContext;
	}

	private String getRemoteAddr(HttpServletRequest request) {
		String remoteIp = request.getHeader(X_REAL_IP); // nginx反向代理
		if (StringUtils.hasText(remoteIp)) {
			return remoteIp;
		}
		else {
			remoteIp = request.getHeader(X_FORWARDED_FOR);// apache反射代理
			if (StringUtils.hasText(remoteIp)) {
				String[] ips = remoteIp.split(",");
				for (String ip : ips) {
					if (!"null".equalsIgnoreCase(ip)) {
						return ip;
					}
				}
			}
			return request.getRemoteAddr();
		}
	}

	/**
	 * 将{@link HttpServletRequest}的数据绑定到
	 * {@link com.wondersgroup.roa.context.ROARequestContext}的
	 * {@link com.wondersgroup.roa.context.ROARequest}中，同时使用 JSR
	 * 303对请求数据进行校验，将错误信息设置到
	 * {@link com.wondersgroup.roa.context.ROARequestContext}的属性列表中。
	 * 
	 * @param roaRequestContext
	 */
	@Override
	public void bindBusinessParams(ROARequestContext roaRequestContext) {
		AbstractROARequest roaRequest = null;
		if (roaRequestContext.getServiceMethodHandler().isROARequestImplType()) {
			HttpServletRequest request = (HttpServletRequest) roaRequestContext.getRawRequestObject();
			BindingResult bindingResult = doBind(request, roaRequestContext.getServiceMethodHandler().getRequestType());
			roaRequest = buildROARequestFromBindingResult(roaRequestContext, bindingResult);

			List<ObjectError> allErrors = bindingResult.getAllErrors();
			roaRequestContext.setAttribute(SimpleROARequestContext.SPRING_VALIDATE_ERROR_ATTRNAME, allErrors);
		}
		else {
			roaRequest = new DefaultROARequest();
		}
		roaRequest.setROARequestContext(roaRequestContext);
		roaRequestContext.setROARequest(roaRequest);
	}

	private String getFormat(HttpServletRequest servletRequest) {
		String messageFormat = servletRequest.getParameter(SystemParameterNames.getFormat());
		if (messageFormat == null) {
			return MessageFormat.xml.name();
		}
		else {
			return messageFormat;
		}
	}

	public static Locale getLocale(HttpServletRequest webRequest) {
		if (webRequest.getParameter(SystemParameterNames.getLocale()) != null) {
			try {
				LocaleEditor localeEditor = new LocaleEditor();
				localeEditor.setAsText(webRequest.getParameter(SystemParameterNames.getLocale()));
				Locale locale = (Locale) localeEditor.getValue();
				if (isValidLocale(locale)) {
					return locale;
				}
			}
			catch (Exception e) {
				return Locale.SIMPLIFIED_CHINESE;
			}
		}
		return Locale.SIMPLIFIED_CHINESE;
	}

	private static boolean isValidLocale(Locale locale) {
		if (Locale.SIMPLIFIED_CHINESE.equals(locale) || Locale.ENGLISH.equals(locale)) {
			return true;
		}
		else {
			try {
				MainErrors.getError(MainErrorType.INVALID_APP_KEY, locale);
			}
			catch (Exception e) {
				return false;
			}
			return true;
		}
	}

	public static MessageFormat getResponseFormat(HttpServletRequest servletRequest) {
		String messageFormat = servletRequest.getParameter(SystemParameterNames.getFormat());
		if (MessageFormat.isValidFormat(messageFormat)) {
			return MessageFormat.getFormat(messageFormat);
		}
		else {
			return MessageFormat.xml;
		}
	}

	private AbstractROARequest buildROARequestFromBindingResult(ROARequestContext roaRequestContext,
			BindingResult bindingResult) {
		AbstractROARequest roaRequest = (AbstractROARequest) bindingResult.getTarget();
		if (roaRequest instanceof AbstractROARequest) {
			AbstractROARequest abstractROARequest = roaRequest;
			abstractROARequest.setROARequestContext(roaRequestContext);
		}
		else {
			logger.warn(roaRequest.getClass().getName() + "不是扩展于" + AbstractROARequest.class.getName() + ",无法设置"
					+ ROARequestContext.class.getName());
		}
		return roaRequest;
	}

	private HashMap<String, String> getRequestParams(HttpServletRequest request) {
		Map srcParamMap = request.getParameterMap();
		HashMap<String, String> destParamMap = new HashMap<String, String>(srcParamMap.size());
		for (Object obj : srcParamMap.keySet()) {
			String[] values = (String[]) srcParamMap.get(obj);
			if (values != null && values.length > 0) {
				destParamMap.put((String) obj, values[0]);
			}
			else {
				destParamMap.put((String) obj, null);
			}
		}
		return destParamMap;
	}

	private BindingResult doBind(HttpServletRequest webRequest, Class<? extends ROARequest> requestType) {
		ROARequest bindObject = BeanUtils.instantiateClass(requestType);
		ServletRequestDataBinder dataBinder = new ServletRequestDataBinder(bindObject, "bindObject");
		dataBinder.setConversionService(getFormattingConversionService());
		dataBinder.setValidator(getValidator());
		dataBinder.bind(webRequest);
		dataBinder.validate();
		return dataBinder.getBindingResult();
	}

	private Validator getValidator() {
		if (this.validator == null) {
			LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
			localValidatorFactoryBean.afterPropertiesSet();
			this.validator = localValidatorFactoryBean;
		}
		return this.validator;
	}

	public FormattingConversionService getFormattingConversionService() {
		return conversionService;
	}

	// 默认的{@link ROARequest}实现类
	private class DefaultROARequest extends AbstractROARequest {
	}
}
