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
 * 是否需求进行签名校验.{@link #DEFAULT}是系统预留的，请不要在实际中使用
 * 
 * @author Jacky.Li
 * 
 */
public enum IgnoreSignType {

	YES, NO, DEFAULT;

	public static boolean isIgnoreSign(IgnoreSignType type) {
		if (NO == type || DEFAULT == type) {
			return false;
		}
		else {
			return true;
		}
	}
}
