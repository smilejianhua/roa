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

import com.wondersgroup.roa.context.ROARequestContext;

/**
 * 在执行服务方法之前产生的事件
 * 
 * @author Jacky.Li
 */
public class PreDoServiceEvent extends ROAEvent {

	private static final long serialVersionUID = 7373801316901457871L;

	private ROARequestContext roaRequestContext;

    public PreDoServiceEvent(Object source, ROARequestContext roaRequestContext) {
        super(source, roaRequestContext.getROAContext());
        this.roaRequestContext = roaRequestContext;
    }

    public ROARequestContext getROARequestContext() {
        return roaRequestContext;
    }

    public long getServiceBeginTime() {
        return roaRequestContext.getServiceBeginTime();
    }
}

