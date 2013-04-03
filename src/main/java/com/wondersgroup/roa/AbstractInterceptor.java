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
 * 抽象拦截器，实现类仅需覆盖特定的方法即可。
 * 
 * @author Jacky.Li
 */
public abstract class AbstractInterceptor implements Interceptor {

	public void beforeService(ROARequestContext roaRequestContext) {
	}

	public void beforeResponse(ROARequestContext roaRequestContext) {
	}

	@Override
	public boolean isMatch(ROARequestContext roaRequestContext) {
		return true;
	}

	/**
	 * 放在拦截器链的最后
	 * 
	 * @return
	 */
	public int getOrder() {
		return Integer.MAX_VALUE;
	}
}
