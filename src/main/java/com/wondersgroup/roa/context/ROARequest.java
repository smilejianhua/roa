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
 * ROA服务的请求对象，请求方法的入参必须是继承于该类。
 * 
 * @author Jacky.Li
 */
public interface ROARequest {

	/**
	 * 获取服务方法的上下文
	 * 
	 * @return
	 */
	public ROARequestContext getROARequestContext();

}
