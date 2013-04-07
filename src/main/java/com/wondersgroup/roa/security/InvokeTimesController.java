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

import com.wondersgroup.roa.session.Session;

/**
 * 服务访问次数及频率的控制管理器
 * 
 * @author Jacky.Li
 */
public interface InvokeTimesController {

    /**
     * 计算应用、会话及用户服务调度总数
     * @param apiKey
     * @param session
     */
    public void caculateInvokeTimes(String apiKey, Session session);

    /**
     * 用户服务访问次数是否超限
     * @param session
     * @return
     */
    public boolean isUserInvokeLimitExceed(String apiKey, Session session);

    /**
     * 某个会话的服务访问次数是否超限
     * @param sessionId
     * @return
     */
    public boolean isSessionInvokeLimitExceed(String apiKey, String sessionId);

    /**
     * 应用的服务访问次数是否超限
     * @param apiKey
     * @return
     */
    public boolean isAppInvokeLimitExceed(String apiKey);

    /**
     * 应用对服务的访问频率是否超限
     * @param apiKey
     * @return
     */
    public boolean isAppInvokeFrequencyExceed(String apiKey);
}

