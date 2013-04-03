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

import java.util.ArrayList;
import java.util.List;

import com.wondersgroup.roa.security.MainError;
import com.wondersgroup.roa.security.SubError;

public class SimpleMainError implements MainError {

	private String code;

	private String message;

	private String solution;

	private List<SubError> subErrors = new ArrayList<SubError>();

	public SimpleMainError(String code, String message, String solution) {
		this.code = code;
		this.message = message;
		this.solution = solution;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getSolution() {
		return solution;
	}

	public List<SubError> getSubErrors() {
		return this.subErrors;
	}

	public void setSubErrors(List<SubError> subErrors) {
		this.subErrors = subErrors;
	}

	public MainError addSubError(SubError subError) {
		this.subErrors.add(subError);
		return this;
	}
}
