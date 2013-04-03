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


/**
 * 更改请求对象创建{@link ROARequestContext}实例,子类可以根据多种传输协议定义自己的创建器。
 * 
 * @author Jacky.Li
 */
public interface RequestContextBuilder {

	/**
	 * 根据reqeuest请求对象，创建{@link ROARequestContext}实例。绑定系统参数，请求对象
	 * 
	 * @param roaContext
	 * @param request
	 * @return
	 */
	public ROARequestContext buildBySysParams(ROAContext roaContext, Object request);

	/**
	 * 绑定业务参数
	 * 
	 * @param roaRequestContext
	 * @param request
	 * @param conversionService
	 */
	public void bindBusinessParams(ROARequestContext roaRequestContext);
}
