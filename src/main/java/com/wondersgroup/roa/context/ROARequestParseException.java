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
 * 对请求数据进行解析时发生异常
 * 
 * @author Jacky.Li
 */
public class ROARequestParseException extends ROAException {

	private static final long serialVersionUID = -493362412852648976L;

	private String requestMessage;

	public ROARequestParseException(String requestMessage) {
		this(requestMessage, "");
	}

	public ROARequestParseException(String requestMessage, String message) {
		this(requestMessage, message, null);
	}

	public ROARequestParseException(String requestMessage, String message, Throwable cause) {
		super(message, cause);
		this.requestMessage = requestMessage;
	}

	public ROARequestParseException(String requestMessage, Throwable cause) {
		this(requestMessage, null, cause);
	}
}
