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
import com.wondersgroup.roa.security.SubError;
import com.wondersgroup.roa.security.SubErrorType;
import com.wondersgroup.roa.security.SubErrors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Locale;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "error")
public class TimeoutErrorResponse extends ErrorResponse {

	private static final String ISP = "isp.";

	private static final String SERVICE_TIMEOUT = "-service-timeout";

	public TimeoutErrorResponse() {
	}

	public TimeoutErrorResponse(String method, Locale locale, int timeout) {
		MainError mainError = SubErrors.getMainError(SubErrorType.ISP_SERVICE_TIMEOUT, locale);
		ArrayList<SubError> subErrors = new ArrayList<SubError>();
		String errorCodeKey = ISP + transform(method) + SERVICE_TIMEOUT;
		SubError subError = SubErrors.getSubError(errorCodeKey, SubErrorType.ISP_SERVICE_TIMEOUT.value(), locale,
				method, timeout);
		subErrors.add(subError);
		setSubErrors(subErrors);
		setMainError(mainError);
	}
}
