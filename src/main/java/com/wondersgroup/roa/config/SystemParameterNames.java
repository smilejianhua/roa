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

/**
 * 系统级参数的名称
 * 
 * @author Jacky.Li
 */
public class SystemParameterNames {

    //方法的默认参数名
    private static final String METHOD = "method";

    //格式化默认参数名
    private static final String FORMAT = "format";

    //本地化默认参数名
    private static final String LOCALE = "locale";

    //会话id默认参数名
    private static final String SESSION_ID = "sessionId";

    //应用键的默认参数名        ;
    private static final String API_KEY = "apiKey";

    //服务版本号的默认参数名
    private static final String VERSION = "v";

    //签名的默认参数名
    private static final String SIGN = "sign";

    private static String method = METHOD;

    private static String format = FORMAT;

    private static String locale = LOCALE;

    private static String sessionId = SESSION_ID;
    
    private static String apiKey = API_KEY;

    private static String version = VERSION;

    private static String sign = SIGN;

    public static String getMethod() {
        return method;
    }

    public static void setMethod(String method) {
        SystemParameterNames.method = method;
    }

    public static String getFormat() {
        return format;
    }

    public static void setFormat(String format) {
        SystemParameterNames.format = format;
    }

    public static String getLocale() {
        return locale;
    }

    public static void setLocale(String locale) {
        SystemParameterNames.locale = locale;
    }

    public static String getSessionId() {
        return sessionId;
    }

    public static void setSessionId(String sessionId) {
        SystemParameterNames.sessionId = sessionId;
    }

    public static String getApiKey() {
        return apiKey;
    }

    public static void setApiKey(String apiKey) {
        SystemParameterNames.apiKey = apiKey;
    }

    public static String getVersion() {
        return version;
    }

    public static void setVersion(String version) {
        SystemParameterNames.version = version;
    }

    public static String getSign() {
        return sign;
    }

    public static void setSign(String sign) {
        SystemParameterNames.sign = sign;
    }
}

