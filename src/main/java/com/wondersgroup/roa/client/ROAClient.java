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
package com.wondersgroup.roa.client;

import com.wondersgroup.roa.request.ROAConverter;

public interface ROAClient {

	/**
	 * 添加自定义的转换器
	 * 
	 * @param roaConverter
	 */
	public void addROAConvertor(ROAConverter roaConverter);

	/**
	 * 设置method系统参数的参数名，下同
	 * 
	 * @param paramName
	 * @return
	 */
	public ROAClient setAppKeyParamName(String paramName);

	/**
	 * 设置sessionId的参数名
	 * 
	 * @param paramName
	 * @return
	 */
	public ROAClient setSessionIdParamName(String paramName);

	/**
	 * 设置method的参数名
	 * 
	 * @param paramName
	 * @return
	 */
	public ROAClient setMethodParamName(String paramName);

	/**
	 * 设置version的参数名
	 * 
	 * @param paramName
	 * @return
	 */
	public ROAClient setVersionParamName(String paramName);

	/**
	 * 设置format的参数名
	 * 
	 * @param paramName
	 * @return
	 */
	public ROAClient setFormatParamName(String paramName);

	/**
	 * 设置locale的参数名
	 * 
	 * @param paramName
	 * @return
	 */
	public ROAClient setLocaleParamName(String paramName);

	/**
	 * 设置sign的参数名
	 * 
	 * @param paramName
	 * @return
	 */
	public ROAClient setSignParamName(String paramName);

	/**
	 * 设置sessionId
	 * 
	 * @param sessionId
	 */
	public void setSessionId(String sessionId);

	/**
	 * 创建一个新的服务请求
	 * 
	 * @return
	 */
	public ClientRequest buildClientRequest();
}
