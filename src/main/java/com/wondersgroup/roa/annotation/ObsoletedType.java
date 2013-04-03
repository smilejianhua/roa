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
 * 服务方法是否已经过期，过期的服务方法不能再访问
 * 
 * @author Jacky.Li
 * 
 */
public enum ObsoletedType {
	YES, NO, DEFAULT;

	public static boolean isObsoleted(ObsoletedType type) {
		if (YES == type) {
			return true;
		}
		else {
			return false;
		}
	}
}
