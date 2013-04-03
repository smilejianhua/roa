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
package com.wondersgroup.roa.request;

import org.springframework.core.convert.converter.Converter;

/**
 * 
 * @author Jacky.Li
 *
 * @param <S>
 * @param <T>
 */
public interface ROAConverter<S, T> extends Converter<S, T> {

    /**
     * 从T转换成S
     * @param target
     * @return
     */
    S unconvert(T target);

    /**
     * 获取源类型
     * @return
     */
    Class<S> getSourceClass();

    /**
     * 获取目标类型
     * @return
     */
    Class<T> getTargetClass();
}

