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
 * 安全控制控制器，决定用户是否有。
 * 
 * @author Jacky.Li
 *
 */
public interface ServiceAccessController {

    /**
     * 服务方法是否向ISV开放
     * @param method
     * @param userId
     * @return
     */
    public boolean isAppGranted(String apiKey, String method, String version);

    /**
     *  服务方法是否向当前用户开放
     * @param ropRequestContext
     * @return
     */
    public boolean isUserGranted(Session session,String method,String version);
}

