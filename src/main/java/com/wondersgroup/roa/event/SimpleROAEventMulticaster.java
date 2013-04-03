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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;

/**
 * 
 * @author Jacky.Li
 */
public class SimpleROAEventMulticaster extends AbstractROAEventMulticaster {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private Executor executor;

	@Override
	public void multicastEvent(final ROAEvent event) {
		try {
			for (final ROAEventListener listener : getROAEventListeners(event)) {
				Executor executor = getExecutor();
				if (executor != null) {
					executor.execute(new Runnable() {
						@Override
						public void run() {
							listener.onROAEvent(event);
						}
					});
				}
				else {
					listener.onROAEvent(event);
				}
			}
		}
		catch (Exception e) {
			logger.error("处理" + event.getClass().getName() + "事件发生异常", e);
		}
	}

	public Executor getExecutor() {
		return executor;
	}

	public void setExecutor(Executor executor) {
		this.executor = executor;
	}
}
