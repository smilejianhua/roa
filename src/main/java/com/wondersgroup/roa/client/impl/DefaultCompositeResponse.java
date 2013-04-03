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
package com.wondersgroup.roa.client.impl;

import com.wondersgroup.roa.client.CompositeResponse;
import com.wondersgroup.roa.response.ErrorResponse;

public class DefaultCompositeResponse<T> implements CompositeResponse {

	private boolean successful;

	private ErrorResponse errorResponse;

	private T successROAResponse;

	public DefaultCompositeResponse(boolean successful) {
		this.successful = successful;
	}

	public ErrorResponse getErrorResponse() {
		return this.errorResponse;
	}

	public T getSuccessResponse() {
		return this.successROAResponse;
	}

	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}

	public void setSuccessROAResponse(T successROAResponse) {
		this.successROAResponse = successROAResponse;
	}

	public boolean isSuccessful() {
		return successful;
	}
}
