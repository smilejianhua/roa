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

import org.springframework.aop.support.AopUtils;
import org.springframework.core.GenericTypeResolver;

public class GenericROAEventAdapter implements SmartROAEventListener {

	private final ROAEventListener delegate;

	public GenericROAEventAdapter(ROAEventListener delegate) {
		this.delegate = delegate;
	}

	@Override
	public boolean supportsEventType(Class<? extends ROAEvent> eventType) {
		Class typeArg = GenericTypeResolver.resolveTypeArgument(this.delegate.getClass(), ROAEventListener.class);
		if (typeArg == null || typeArg.equals(ROAEvent.class)) {
			Class targetClass = AopUtils.getTargetClass(this.delegate);
			if (targetClass != this.delegate.getClass()) {
				typeArg = GenericTypeResolver.resolveTypeArgument(targetClass, ROAEventListener.class);
			}
		}
		return (typeArg == null || typeArg.isAssignableFrom(eventType));
	}

	@Override
	public void onROAEvent(ROAEvent roaEvent) {
		this.delegate.onROAEvent(roaEvent);
	}

	@Override
	public int getOrder() {
		return Integer.MAX_VALUE;
	}
}
