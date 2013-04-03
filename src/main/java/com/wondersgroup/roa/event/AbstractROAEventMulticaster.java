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

import java.util.*;

public abstract class AbstractROAEventMulticaster implements ROAEventMulticaster {

	private Set<ROAEventListener> roaEventListeners = new HashSet<ROAEventListener>();

	private static final Map<Class<? extends ROAEvent>, ListenerRegistry> cachedROAEventListeners = new HashMap<Class<? extends ROAEvent>, ListenerRegistry>();

	@Override
	public void removeAllROAListeners() {
		roaEventListeners.clear();
	}

	@Override
	public void addROAListener(ROAEventListener listener) {
		roaEventListeners.add(listener);
	}

	@Override
	public void removeROAListener(ROAEventListener listener) {
		roaEventListeners.remove(listener);
	}

	protected List<ROAEventListener> getROAEventListeners(ROAEvent event) {
		Class<? extends ROAEvent> eventType = event.getClass();
		if (!cachedROAEventListeners.containsKey(eventType)) {
			LinkedList<ROAEventListener> allListeners = new LinkedList<ROAEventListener>();
			if (roaEventListeners != null && roaEventListeners.size() > 0) {
				for (ROAEventListener roaEventListener : roaEventListeners) {
					if (supportsEvent(roaEventListener, eventType)) {
						allListeners.add(roaEventListener);
					}
				}
				sortROAEventListener(allListeners);
			}
			ListenerRegistry listenerRegistry = new ListenerRegistry(allListeners);
			cachedROAEventListeners.put(eventType, listenerRegistry);
		}
		return cachedROAEventListeners.get(eventType).getROAEventListeners();
	}

	protected boolean supportsEvent(ROAEventListener listener, Class<? extends ROAEvent> eventType) {
		SmartROAEventListener smartListener = (listener instanceof SmartROAEventListener ? (SmartROAEventListener) listener
				: new GenericROAEventAdapter(listener));
		return (smartListener.supportsEventType(eventType));
	}

	protected void sortROAEventListener(List<ROAEventListener> roaEventListeners) {
		Collections.sort(roaEventListeners, new Comparator<ROAEventListener>() {
			public int compare(ROAEventListener o1, ROAEventListener o2) {
				if (o1.getOrder() > o2.getOrder()) {
					return 1;
				}
				else if (o1.getOrder() < o2.getOrder()) {
					return -1;
				}
				else {
					return 0;
				}
			}
		});
	}

	private class ListenerRegistry {

		public List<ROAEventListener> roaEventListeners;

		private ListenerRegistry(List<ROAEventListener> roaEventListeners) {
			this.roaEventListeners = roaEventListeners;
		}

		public List<ROAEventListener> getROAEventListeners() {
			return roaEventListeners;
		}
	}
}
