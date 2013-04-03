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

import org.apache.commons.codec.binary.Base64;

/**
 * ROA的上传文件编码格式为：fileType@BASE64编码的文件内容
 * 
 * @author Jacky.Li
 */
public class UploadFileUtils {

	public static final char SPERATOR = '@';

	/**
	 * 获取文件的类型
	 * 
	 * @param encodeFile
	 * @return
	 */
	public static final String getFileType(String encodeFile) {
		int speratorIndex = encodeFile.indexOf(SPERATOR);
		if (speratorIndex > -1) {
			String fileType = encodeFile.substring(0, speratorIndex);
			return fileType.toLowerCase();
		}
		else {
			throw new IllegalUploadFileFormatException("文件格式不对，正确格式为：<文件格式>@<文件内容>");
		}
	}

	/**
	 * 获取文件的字节数组
	 * 
	 * @param encodeFile
	 * @return
	 */
	public static final byte[] decode(String encodeFile) {
		int speratorIndex = encodeFile.indexOf(SPERATOR);
		if (speratorIndex > -1) {
			String content = encodeFile.substring(speratorIndex + 1);
			return Base64.decodeBase64(content);
		}
		else {
			throw new IllegalUploadFileFormatException("文件格式不对，正确格式为：<文件格式>@<文件内容>");
		}
	}

	/**
	 * 将文件编码为BASE64的字符串
	 * 
	 * @param bytes
	 * @return
	 */
	public static final String encode(byte[] bytes) {
		return Base64.encodeBase64String(bytes);
	}

	/**
	 * 将文件编码为一个字符串
	 * 
	 * @param uploadFile
	 * @return
	 */
	public static final String encode(UploadFile uploadFile) {
		StringBuilder sb = new StringBuilder();
		sb.append(uploadFile.getFileType());
		sb.append(SPERATOR);
		sb.append(encode(uploadFile.getContent()));
		return sb.toString();
	}
}
