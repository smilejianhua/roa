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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Locale;

/**
 * 当服务平台资源耗尽（超过最大线程数且列队排满后）
 * 
 * @author Jacky.Li
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "error")
public class RejectedServiceResponse extends ErrorResponse {

	public RejectedServiceResponse() {
	}

	public RejectedServiceResponse(Locale locale) {
		MainError mainError = MainErrors.getError(MainErrorType.FORBIDDEN_REQUEST, locale);
		setMainError(mainError);
	}
}
