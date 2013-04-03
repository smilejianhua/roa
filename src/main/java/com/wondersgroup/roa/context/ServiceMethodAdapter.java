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
 * 通过该适配器以统一的方式调用ROA方法
 * 
 * @author Jacky.Li
 *
 */
public interface ServiceMethodAdapter {
	
	/**
	 * 调用服务方法
	 * 
	 * @param roaRequestContext
	 * @return
	 */
	public Object invokeServiceMethod(ROARequestContext roaRequestContext);

}
