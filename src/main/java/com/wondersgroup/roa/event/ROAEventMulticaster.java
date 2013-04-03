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

/**
 * 注册事件监听器，发布事件
 * 
 * @author Jacky.Li
 */
public interface ROAEventMulticaster {

	/**
	 * Add a listener to be notified of all events.
	 * 
	 * @param listener
	 *            the listener to add
	 */
	public void addROAListener(ROAEventListener listener);

	/**
	 * Remove a listener from the notification list.
	 * 
	 * @param listener
	 *            the listener to remove
	 */
	public void removeROAListener(ROAEventListener listener);

	/**
	 * Remove all listeners registered with this multicaster.
	 * <p>
	 * After a remove call, the multicaster will perform no action on event
	 * notification until new listeners are being registered.
	 */
	public void removeAllROAListeners();

	/**
	 * Multicast the given application event to appropriate listeners.
	 * 
	 * @param event
	 *            the event to multicast
	 */
	public void multicastEvent(ROAEvent event);
}
