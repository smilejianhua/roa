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

import com.wondersgroup.roa.annotation.IgnoreSign;

import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;

/**
 * 上传的文件
 * 
 * @author Jacky.Li
 */
@IgnoreSign
public class UploadFile {

	private String fileType;

	private byte[] content;

	/**
	 * 根据文件内容构造
	 * 
	 * @param content
	 */
	public UploadFile(String fileType, byte[] content) {
		this.content = content;
		this.fileType = fileType;
	}

	/**
	 * 根据文件构造
	 * 
	 * @param file
	 */
	public UploadFile(File file) {
		try {
			this.content = FileCopyUtils.copyToByteArray(file);
			this.fileType = file.getName().substring(file.getName().lastIndexOf('.') + 1);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getFileType() {
		return fileType;
	}

	public byte[] getContent() {
		return content;
	}
}
