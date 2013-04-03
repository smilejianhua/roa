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
package com.wondersgroup.roa;

import com.wondersgroup.roa.context.ROARequestContext;

/**
 * 拦截器，将在服务之前，服务之后响应之前调用
 * 
 * @author Jacky.Li
 *
 */
public interface Interceptor {

	/**
	 * 在进行服务之前调用,如果在方法中往{@link ROARequestContext}设置了{@link ROAResponse}
	 * （相当于已经产生了响应了）, 所以服务将直接返回，不往下继续执行，反之服务会继续往下执行直到返回响应
	 * 
	 * @param roaRequestContext
	 */
	public void beforeService(ROARequestContext roaRequestContext);

	/**
	 * 在服务之后，响应之前调用
	 * 
	 * @param roaRequestContext
	 */
	public void beforeResponse(ROARequestContext roaRequestContext);

	/**
	 * 该方法返回true时才实施拦截，否则不拦截。可以通过{@link ROARequestContext}
	 * 
	 * @param roaRequestContext
	 * @return
	 */
	boolean isMatch(ROARequestContext roaRequestContext);

	/**
	 * 执行的顺序，整数值越小的越早执行
	 * 
	 * @return
	 */
	int getOrder();
}
