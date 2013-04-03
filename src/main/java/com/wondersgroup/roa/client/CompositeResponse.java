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

import com.wondersgroup.roa.response.ErrorResponse;

/**
 * 客户端的响应,如果{@link #isSuccessful()}返回true，则调用{@link #getErrorResponse()}，反之，则应该
 * 调用{@link #getSuccessResponse(Class)}
 * 
 * @author Jacky.Li
 *
 * @param <T>
 */
public interface CompositeResponse<T> {

    /**
     * 获取错误的响应对象
     *
     * @return
     */
    public ErrorResponse getErrorResponse();

    /**
     * 获取正确的响应对象
     *
     * @param responseClass
     * @param <T>
     * @return
     */
    public T getSuccessResponse();

    /**
     * 响应是否是正确的
     *
     * @return
     */
    public boolean isSuccessful();
}

