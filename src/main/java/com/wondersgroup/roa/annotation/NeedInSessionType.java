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
package com.wondersgroup.roa.annotation;

/**
 * 功能说明：是否需求会话检查
 * 
 * @author Jacky.Li
 */
public enum NeedInSessionType {
	
	YES, NO, DEFAULT;

	public static boolean isNeedInSession(NeedInSessionType type) {
		if (YES == type || DEFAULT == type) {
			return true;
		}
		else {
			return false;
		}
	}
}
