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

import com.wondersgroup.roa.context.ROARequestContext;
import com.wondersgroup.roa.context.ServiceMethodAdapter;
import com.wondersgroup.roa.context.ServiceMethodHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.InvocationTargetException;

/**
 * 通过该服务方法适配器调用目标的服务方法
 * 
 * @author Jacky.Li
 */
public class AnnotationServiceMethodAdapter implements ServiceMethodAdapter {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

	/**
	 * 调用ROA服务方法
	 * 
	 * @param roaRequestContext
	 * @return
	 */
	public Object invokeServiceMethod(ROARequestContext roaRequestContext) {
		try {
			// 分析上下文中的错误
			ServiceMethodHandler serviceMethodHandler = roaRequestContext.getServiceMethodHandler();
			if (logger.isDebugEnabled()) {
				logger.debug("执行" + serviceMethodHandler.getHandler().getClass() + "."
						+ serviceMethodHandler.getHandlerMethod().getName());
			}
			if (serviceMethodHandler.isHandlerMethodWithParameter()) {
				return serviceMethodHandler.getHandlerMethod().invoke(serviceMethodHandler.getHandler(),
						roaRequestContext.getROARequest());
			}
			else {
				return serviceMethodHandler.getHandlerMethod().invoke(serviceMethodHandler.getHandler());
			}
		}
		catch (Throwable e) {
			if (e instanceof InvocationTargetException) {
				InvocationTargetException inve = (InvocationTargetException) e;
				throw new RuntimeException(inve.getTargetException());
			}
			else {
				throw new RuntimeException(e);
			}
		}
	}

}
