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
package com.wondersgroup.roa.client;

import com.wondersgroup.roa.context.ROARequest;

/**
 * 每个请求对应一个ClientRequest对象
 * 
 * @author Jacky.Li
 */
public interface ClientRequest {

    /**
     * 添加请求参数,默认需要签名，如果类已经标注了{@link com.wondersgroup.roa.annotation.IgnoreSign}则始终不加入签名
     * @param paramName
     * @param paramValue
     * @return
     */
    public ClientRequest addParam(String paramName,Object paramValue);

    /**
     * 添加请求参数,如果needSign=false表示不参于签名
     * @param paramName
     * @param paramValue
     * @param needSign
     * @return
     */
    public ClientRequest addParam(String paramName,Object paramValue,boolean needSign);

    /**
     * 清除参数列表
     * @return
     */
    public ClientRequest clearParam();

    /**
     * 使用POST发起请求
     * @param roaResponseClass
     * @param methodName
     * @param version
     * @param <T>
     * @return
     */
    public <T> CompositeResponse post(Class<T> roaResponseClass, String methodName, String version);

    /**
     * 直接使用 roaRequest发送请求
     * @param roaRequest
     * @param roaResponseClass
     * @param methodName
     * @param version
     * @param <T>
     * @return
     */
    public <T> CompositeResponse post(ROARequest roaRequest, Class<T> roaResponseClass, String methodName, String version);

    /**
     * 使用GET发送服务请求
     * @param roaResponseClass
     * @param methodName
     * @param version
     * @param <T>
     * @return
     */
    public <T> CompositeResponse get(Class<T> roaResponseClass, String methodName, String version);

    /**
     * 使用GET发送roaRequest的请求
     * @param roaRequest
     * @param roaResponseClass
     * @param methodName
     * @param version
     * @param <T>
     * @return
     */
    public <T> CompositeResponse get(ROARequest roaRequest, Class<T> roaResponseClass, String methodName, String version);
}

