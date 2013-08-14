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

import com.wondersgroup.roa.context.ROAException;
import com.wondersgroup.roa.security.ApiSecretManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * 基于文件管理的应用密钥
 * 
 * @author Jacky.Li
 */
public class FileBaseApiSecretManager implements ApiSecretManager {

	private static final String ROA_API_SECRET_PROPERTIES = "roa.apiSecret.properties";

	private String apiSecretFile = ROA_API_SECRET_PROPERTIES;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private Properties properties;

	public String getSecret(String appKey) {
		if (properties == null) {
			try {
				DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
				Resource resource = resourceLoader.getResource(apiSecretFile);
				properties = PropertiesLoaderUtils.loadProperties(resource);
			}
			catch (IOException e) {
				throw new ROAException("在类路径下找不到roa.appSecret.properties的应用密钥的属性文件", e);
			}
		}
		String secret = properties.getProperty(appKey);
		if (secret == null) {
			logger.error("不存在应用键为{0}的密钥,请检查应用密钥的配置文件。", appKey);
		}
		return secret;
	}

	public void setApiSecretFile(String apiSecretFile) {
		this.apiSecretFile = apiSecretFile;
	}

	@Override
	public boolean isValidAppKey(String appKey) {
		return getSecret(appKey) != null;
	}
}
