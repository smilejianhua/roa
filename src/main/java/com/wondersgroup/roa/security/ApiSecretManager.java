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

/**
 * 应用键管理器，可根据apiKey获取对应的secret.
 * 
 * @author Jacky.Li
 */
public interface ApiSecretManager {

    /**
     * 获取应用程序的密钥
     *
     * @param apiKey
     * @return
     */
    public String getSecret(String apiKey);

    /**
     * 是否是合法的apiKey
     *
     * @param apiKey
     * @return
     */
    public boolean isValidApiKey(String apiKey);
}

