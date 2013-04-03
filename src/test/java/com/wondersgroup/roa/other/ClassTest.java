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

import static org.junit.Assert.*;

import org.junit.Test;

import com.wondersgroup.roa.AbstractROARequest;
import com.wondersgroup.roa.context.ROARequest;

public class ClassTest {

    @Test
    public void testAssignableFrom() {
        assertTrue(!MyROARequest.class.isAssignableFrom(ROARequest.class));
        assertTrue(!MyROARequest.class.isAssignableFrom(AbstractROARequest.class));
        assertTrue(AbstractROARequest.class.isAssignableFrom(MyROARequest.class));
    }

    @Test
    public void modeInt() {
        int len = 16 - 1;
        for (int i = 0; i < 100; i++) {
            int i1 = i & len;
            System.out.println("i:" + i1);
            assertTrue(i1 <= len);
        }
    }

    private class MyROARequest extends AbstractROARequest {

    }

}

