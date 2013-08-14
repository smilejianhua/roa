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
package com.wondersgroup.roa.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * 指定自定义的系统参数名
 * 
 * @author Jacky.Li
 */
public class SystemParameterNamesBeanDefinitionParser implements BeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		String appKey = element.getAttribute("appkey-param-name");
		String sessionId = element.getAttribute("sessionid-param-name");
		String method = element.getAttribute("method-param-name");
		String version = element.getAttribute("version-param-name");
		String format = element.getAttribute("format-param-name");
		String locale = element.getAttribute("locale-param-name");
		String sign = element.getAttribute("sign-param-name");

		if (StringUtils.hasText(appKey)) {
			SystemParameterNames.setAppKey(appKey);
		}
		if (StringUtils.hasText(sessionId)) {
			SystemParameterNames.setSessionId(sessionId);
		}
		if (StringUtils.hasText(method)) {
			SystemParameterNames.setMethod(method);
		}
		if (StringUtils.hasText(version)) {
			SystemParameterNames.setVersion(version);
		}
		if (StringUtils.hasText(format)) {
			SystemParameterNames.setFormat(format);
		}
		if (StringUtils.hasText(locale)) {
			SystemParameterNames.setLocale(locale);
		}
		if (StringUtils.hasText(sessionId)) {
			SystemParameterNames.setSign(sign);
		}
		return null;
	}
}
