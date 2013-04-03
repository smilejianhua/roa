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
package com.wondersgroup.roa.request;

/**
 * 上传文件字符串转换时发生错误
 * 
 * @author Jacky.Li
 */
public class IllegalUploadFileFormatException extends IllegalArgumentException {

	private static final long serialVersionUID = 6677115852203979585L;

	public IllegalUploadFileFormatException() {
		super();
	}

	public IllegalUploadFileFormatException(String s) {
		super(s);
	}

	public IllegalUploadFileFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalUploadFileFormatException(Throwable cause) {
		super(cause);
	}
}
