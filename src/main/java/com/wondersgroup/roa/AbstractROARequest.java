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

import com.wondersgroup.roa.annotation.Temporary;
import com.wondersgroup.roa.context.ROARequest;
import com.wondersgroup.roa.context.ROARequestContext;

/**
 * 所有请求对象应该通过扩展此抽象类实现
 * 
 * @author Jacky.Li
 */
public abstract class AbstractROARequest implements ROARequest {

	@Temporary
	private ROARequestContext roaRequestContext;

	@Override
	public ROARequestContext getROARequestContext() {
		return roaRequestContext;
	}

	public final void setROARequestContext(ROARequestContext roaRequestContext) {
		this.roaRequestContext = roaRequestContext;
	}
}
