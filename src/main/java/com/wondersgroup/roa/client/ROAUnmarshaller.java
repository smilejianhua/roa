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

/**
 * 对响应进行反流化
 * 
 * @author Jacky.Li
 */
public interface ROAUnmarshaller {

    /**
     * 将字符串反序列化为相应的对象
     *
     * @param inputStream
     * @param objectType
     * @param <T>
     * @return
     */
    public <T> T unmarshaller(String content, Class<T> objectType);
}

