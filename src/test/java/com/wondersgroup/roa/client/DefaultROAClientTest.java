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

import org.junit.Test;

import com.wondersgroup.roa.client.ROAClient;
import com.wondersgroup.roa.client.impl.DefaultROAClient;

public class DefaultROAClientTest {

    private ROAClient roaClient = new DefaultROAClient("http://localhost:8088/router", "00001", "abcdeabcdeabcdeabcdeabcde");

    @Test
    public void testPostWithSession() throws Exception {
    	System.out.println(roaClient);
    }
}

