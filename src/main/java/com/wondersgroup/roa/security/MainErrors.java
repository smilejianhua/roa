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
package com.wondersgroup.roa.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.Assert;

import com.wondersgroup.roa.security.impl.SimpleMainError;

import java.util.Locale;

public class MainErrors {

	protected static Logger logger = LoggerFactory.getLogger(MainErrors.class);

	private static final String ERROR_CODE_PREFIX = "ERROR_";
	private static final String ERROR_SOLUTION_SUBFIX = "_SOLUTION";
	// 错误信息的国际化信息
	private static MessageSourceAccessor errorMessageSourceAccessor;

	public static MainError getError(MainErrorType mainErrorType, Locale locale) {
		String errorMessage = getErrorMessage(ERROR_CODE_PREFIX + mainErrorType.value(), locale);
		String errorSolution = getErrorSolution(ERROR_CODE_PREFIX + mainErrorType.value() + ERROR_SOLUTION_SUBFIX,
				locale);
		return new SimpleMainError(mainErrorType.value(), errorMessage, errorSolution);
	}

	public static void setErrorMessageSourceAccessor(MessageSourceAccessor errorMessageSourceAccessor) {
		MainErrors.errorMessageSourceAccessor = errorMessageSourceAccessor;
	}

	private static String getErrorMessage(String code, Locale locale) {
		try {
			Assert.notNull(errorMessageSourceAccessor, "请先设置错误消息的国际化资源");
			return errorMessageSourceAccessor.getMessage(code, new Object[] {}, locale);
		}
		catch (NoSuchMessageException e) {
			logger.error("不存在对应的错误键：{}，请检查是否在i18n/roa/error的错误资源", code);
			throw e;
		}
	}

	private static String getErrorSolution(String code, Locale locale) {
		try {
			Assert.notNull(errorMessageSourceAccessor, "请先设置错误解决方案的国际化资源");
			return errorMessageSourceAccessor.getMessage(code, new Object[] {}, locale);
		}
		catch (NoSuchMessageException e) {
			logger.error("不存在对应的错误键：{}，请检查是否在i18n/roa/error的错误资源", code);
			throw e;
		}
	}

}
