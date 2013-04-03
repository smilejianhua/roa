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

import com.wondersgroup.roa.session.SessionManager;

import java.util.Map;

/**
 * ROA服务方法的处理者的注册表
 * 
 * @author Jacky.Li
 */
public interface ROAContext {

	/**
	 * 注册一个服务处理器
	 * 
	 * @param methodName
	 * @param version
	 * @param serviceMethodHandler
	 */
	public void addServiceMethod(String methodName, String version, ServiceMethodHandler serviceMethodHandler);

	/**
	 * 获取服务处理器
	 * 
	 * @param methodName
	 * @return
	 */
	public ServiceMethodHandler getServiceMethodHandler(String methodName, String version);

	/**
	 * 是否是合法的服务方法
	 * 
	 * @param methodName
	 * @return
	 */
	public boolean isValidMethod(String methodName);

	/**
	 * 是否是合法服务方法版本号
	 * 
	 * @param methodName
	 * @param version
	 * @return
	 */
	public boolean isValidMethodVersion(String methodName, String version);

	/**
	 * 获取所有的处理器列表
	 * 
	 * @return
	 */
	public Map<String, ServiceMethodHandler> getAllServiceMethodHandlers();

	/**
	 * 是开启签名功能
	 * 
	 * @return
	 */
	public boolean isSignEnable();

	/**
	 * 获取会话管理器
	 * 
	 * @return
	 */
	public SessionManager getSessionManager();

}
