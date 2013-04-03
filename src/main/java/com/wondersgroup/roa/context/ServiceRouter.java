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
package com.wondersgroup.roa.context;

import com.wondersgroup.roa.Interceptor;
import com.wondersgroup.roa.ThreadFerry;
import com.wondersgroup.roa.event.ROAEventListener;
import com.wondersgroup.roa.security.InvokeTimesController;
import com.wondersgroup.roa.security.SecurityManager;
import com.wondersgroup.roa.session.SessionManager;

import org.springframework.context.ApplicationContext;
import org.springframework.format.support.FormattingConversionService;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * ROA的服务路由器，服务方法必须位于@Controller的类中，服务方法使用{@link com.wondersgroup.roa.annotation.ServiceMethod}注解，有两个合法的方法签名方式：
 * 签名方式1：有入参，且参数必须实现{@link ROARequest}接口，返回参数为{@link ROAResponse}
 *   <code>
 *    @ServiceMethod("method1")
 *    ROAResponse handleMethod1(ROARequest roaRequest){
 *        ...
 *    }
 *   </code>
 * 签名方式2：无入参，返回参数为{@link ROAResponse}
 *   <code>
 *    @ServiceMethod("method1")
 *    ROAResponse handleMethod1(){
 *        ...
 *    }
 *   </code>
 * ROA框架会自动将请求参数的值绑定到入参请求对象中。
 * 
 * @author Jacky.Li
 *
 */
public interface ServiceRouter {

	/**
	 * ROA框架的总入口，一般框架实现，开发者无需关注。
	 * 
	 * @param webRequest
	 * @param httpServletResponse
	 */
	public void service(Object request, Object response);

	/**
	 * 启动服务路由器
	 */
	public void startup();

	/**
	 * 关闭服务路由器
	 */
	public void shutdown();

	/**
	 * 获取{@link ROAContext}
	 * 
	 * @return
	 */
	public ROAContext getROAContext();

	/**
	 * 设置Spring的上下文
	 * 
	 * @param applicationContext
	 */
	public void setApplicationContext(ApplicationContext applicationContext);

	/**
	 * 注册拦截器
	 * 
	 * @param interceptor
	 */
	public void addInterceptor(Interceptor interceptor);

	/**
	 * 注册监听器
	 * 
	 * @param listener
	 */
	public void addListener(ROAEventListener listener);

	/**
	 * 设置{@link com.wondersgroup.roa.security.SecurityManager}
	 * 
	 * @param securityManager
	 */
	public void setSecurityManager(SecurityManager securityManager);

	/**
	 * 注册
	 * 
	 * @param threadPoolExecutor
	 */
	public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor);

	/**
	 * 设置是否需要进行签名校验
	 * 
	 * @param signEnable
	 */
	public void setSignEnable(boolean signEnable);

	/**
	 * 设置所有服务的通用过期时间，单位为秒
	 * 
	 * @param serviceTimeoutSeconds
	 */
	public void setServiceTimeoutSeconds(int serviceTimeoutSeconds);

	/**
	 * 设置扩展错误资源基名
	 * 
	 * @param extErrorBasename
	 */
	public void setExtErrorBasename(String extErrorBasename);

	/**
	 * 设置格式化类型转换器
	 * 
	 * @param conversionService
	 */
	public void setFormattingConversionService(FormattingConversionService conversionService);

	/**
	 * 添加会话管理器
	 * 
	 * @param sessionManager
	 */
	public void setSessionManager(SessionManager sessionManager);

	/**
	 * 设置服务调用限制管理器
	 * 
	 * @param invokeTimesController
	 */
	public void setInvokeTimesController(InvokeTimesController invokeTimesController);

	/**
	 * 设置线程信息摆渡器
	 * 
	 * @param threadFerryClass
	 */
	public void setThreadFerryClass(Class<? extends ThreadFerry> threadFerryClass);
}
