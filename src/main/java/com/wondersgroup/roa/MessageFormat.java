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

/**
 * 支持的响应的格式类型
 * 
 * @author Jacky.Li
 */
public enum MessageFormat {

	xml, json;

	public static MessageFormat getFormat(String value) {
		if ("json".equalsIgnoreCase(value)) {
			return json;
		}
		else {
			return xml;
		}
	}

	public static boolean isValidFormat(String value) {
		return xml.name().equalsIgnoreCase(value) || json.name().equalsIgnoreCase(value);
	}

}
