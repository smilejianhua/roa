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
package com.wondersgroup.roa.session;

/**
 * 会话管理器
 * 
 * @author Jacky.Li
 */
public interface SessionManager {

    /**
     * 注册一个会话
     *
     * @param session
     */
    public void addSession(String sessionId, Session session);

    /**
     * 从注册表中获取会话
     *
     * @param sessionId
     * @return
     */
    public Session getSession(String sessionId);

    /**
     * 移除这个会话
     *
     * @param sessionId
     * @return
     */
    public void removeSession(String sessionId);
}

