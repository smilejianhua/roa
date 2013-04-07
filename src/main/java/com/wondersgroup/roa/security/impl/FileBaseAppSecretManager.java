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
import com.wondersgroup.roa.security.AppSecretManager;

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
public class FileBaseAppSecretManager implements AppSecretManager {

	private static final String ROA_APP_SECRET_PROPERTIES = "roa.appSecret.properties";

	private String appSecretFile = ROA_APP_SECRET_PROPERTIES;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private Properties properties;

	public String getSecret(String apiKey) {
		if (properties == null) {
			try {
				DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
				Resource resource = resourceLoader.getResource(appSecretFile);
				properties = PropertiesLoaderUtils.loadProperties(resource);
			}
			catch (IOException e) {
				throw new ROAException("在类路径下找不到roa.appSecret.properties的应用密钥的属性文件", e);
			}
		}
		String secret = properties.getProperty(apiKey);
		if (secret == null) {
			logger.error("不存在应用键为{0}的密钥,请检查应用密钥的配置文件。", apiKey);
		}
		return secret;
	}

	public void setAppSecretFile(String appSecretFile) {
		this.appSecretFile = appSecretFile;
	}

	@Override
	public boolean isValidApiKey(String apiKey) {
		return getSecret(apiKey) != null;
	}
}
