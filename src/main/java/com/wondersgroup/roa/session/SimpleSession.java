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
package com.wondersgroup.roa.session;

import java.util.HashMap;
import java.util.Map;

public class SimpleSession implements Session {

	private static final long serialVersionUID = 6898039722389354626L;

	private Map<String, Object> attributes = new HashMap<String, Object>();

	public void setAttribute(String name, Object obj) {
		attributes.put(name, obj);
	}

	public Object getAttribute(String name) {
		return attributes.get(name);
	}
}
