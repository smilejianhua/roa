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
package com.wondersgroup.roa.security.impl;

import com.wondersgroup.roa.security.InvokeTimesController;
import com.wondersgroup.roa.session.Session;

/**
 * 服务访问次数及频率的控制管理器的默认实现
 * 
 * @author Jacky.Li
 */
public class DefaultInvokeTimesController implements InvokeTimesController {

	@Override
	public void caculateInvokeTimes(String appKey, Session session) {
	}

	@Override
	public boolean isUserInvokeLimitExceed(String appKey, Session session) {
		return false;
	}

	@Override
	public boolean isSessionInvokeLimitExceed(String appKey, String sessionId) {
		return false;
	}

	@Override
	public boolean isAppInvokeLimitExceed(String appKey) {
		return false;
	}

	@Override
	public boolean isAppInvokeFrequencyExceed(String appKey) {
		return false;
	}
}
