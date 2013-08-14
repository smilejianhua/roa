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

import com.wondersgroup.roa.security.ServiceAccessController;
import com.wondersgroup.roa.session.Session;

/**
 * 功能说明：对调用的方法进行安全性检查
 * 
 * @author Jacky.Li
 */
public class DefaultServiceAccessController implements ServiceAccessController {

    @Override
    public boolean isAppGranted(String appKey, String method, String version) {
        return true;
    }

    @Override
    public boolean isUserGranted(Session session, String method, String version) {
        return true;
    }
}