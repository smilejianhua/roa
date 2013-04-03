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
package com.wondersgroup.roa.response;

import com.wondersgroup.roa.security.MainError;
import com.wondersgroup.roa.security.MainErrorType;
import com.wondersgroup.roa.security.MainErrors;
import com.wondersgroup.roa.security.SubError;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Locale;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "error")
public class ErrorResponse {

	@XmlAttribute
	protected String code;

	@XmlElement
	protected String message;

	@XmlElement
	protected String solution;

	@XmlElementWrapper(name = "subErrors")
	@XmlElement(name = "subError")
	protected List<SubError> subErrors;

	public ErrorResponse() {
	}

	public ErrorResponse(MainError mainError) {
		this.code = mainError.getCode();
		this.message = mainError.getMessage();
		this.solution = mainError.getSolution();
		if (mainError.getSubErrors() != null && mainError.getSubErrors().size() > 0) {
			this.subErrors = mainError.getSubErrors();
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public List<SubError> getSubErrors() {
		return subErrors;
	}

	public void setSubErrors(List<SubError> subErrors) {
		this.subErrors = subErrors;
	}

	protected MainError getInvalidArgumentsError(Locale locale) {
		return MainErrors.getError(MainErrorType.INVALID_ARGUMENTS, locale);
	}

	protected void setMainError(MainError mainError) {
		setCode(mainError.getCode());
		setMessage(mainError.getMessage());
		setSolution(mainError.getSolution());
	}

	/**
	 * 对服务名进行标准化处理：如book.upload转换为book-upload，
	 * 
	 * @param method
	 * @return
	 */
	protected String transform(String method) {
		if (method != null) {
			method = method.replace(".", "-");
			return method;
		}
		else {
			return "LACK_METHOD";
		}
	}
}
