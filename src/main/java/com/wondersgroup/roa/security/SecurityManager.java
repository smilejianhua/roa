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

import com.wondersgroup.roa.context.ROARequestContext;
import com.wondersgroup.roa.session.SessionManager;

/**
 * 负责对请求数据、会话、业务安全、应用密钥安全进行检查并返回相应的错误。
 * 
 * @author Jacky.Li
 *
 */
public interface SecurityManager {

    /**
     * 对请求服务的上下文进行检查校验
     *
     * @param roaRequestContext
     * @return
     */
    public MainError validateSystemParameters(ROARequestContext roaRequestContext);

    /**
     * 验证其它的事项：包括业务参数，业务安全性，会话安全等
     *
     * @param roaRequestContext
     * @return
     */
    public MainError validateOther(ROARequestContext roaRequestContext);

    /**
     * 获取安全管理器
     *
     * @return
     */
    public void setServiceAccessController(ServiceAccessController serviceAccessController);

    /**
     * 获取应用密钥管理器
     *
     * @return
     */
    public void setAppSecretManager(AppSecretManager appSecretManager);

    /**
     * 设置会话管理器，以验证会话的合法性
     *
     * @return
     */
    public void setSessionManager(SessionManager sessionManager);

    /**
     * 设置服务调度次数管理器
     * @param invokeTimesController
     */
    public void setInvokeTimesController(InvokeTimesController invokeTimesController);

    /**
     * 文件上传控制器
     * @param fileUploadController
     */
    public void setFileUploadController(FileUploadController fileUploadController);
}

