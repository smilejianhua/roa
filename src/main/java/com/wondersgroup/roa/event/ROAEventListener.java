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
package com.wondersgroup.roa.event;

import java.util.EventListener;

/**
 * 监听所有ROA框架的事件
 * @author Jacky.Li
 *
 * @param <E>
 */
public interface ROAEventListener<E extends ROAEvent> extends EventListener {

    /**
     * 响应事件
     *
     * @param roaEvent
     */
    public void onROAEvent(E roaEvent);

    /**
     * 执行的顺序号
     *
     * @return
     */
    public int getOrder();
}

