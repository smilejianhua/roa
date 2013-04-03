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
package com.wondersgroup.roa.other;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FunctionTest {

	@Test
	public void testStringSplit() {
		String str = "A,B";
		String[] items = str.split(",");
		System.out.println("size:" + items.length);
		for (String item : items) {
			System.out.println("item:" + item);
		}
	}

	@Test
	public void testArrayToList() {
		String str = "A,B";
		String[] items = str.split(",");
		List<String> list = Arrays.asList(items);
		System.out.println("size:" + list.size());
		for (String s : list) {
			System.out.println("item:" + s);
		}
	}
}
