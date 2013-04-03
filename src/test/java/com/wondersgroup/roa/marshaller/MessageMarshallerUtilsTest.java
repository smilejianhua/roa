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
package com.wondersgroup.roa.marshaller;

import com.wondersgroup.roa.MessageFormat;
import com.wondersgroup.roa.context.ROARequest;
import com.wondersgroup.roa.context.ROARequestContext;
import com.wondersgroup.roa.marshaller.MessageMarshallerUtils;

import java.util.HashMap;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageMarshallerUtilsTest {

    @Test
    public void testMarshallerROARequest() throws Exception {
        ROARequest roaRequest = mock(ROARequest.class);
        ROARequestContext msc = mock(ROARequestContext.class);
        when(roaRequest.getROARequestContext()).thenReturn(msc);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key1", "key1Value");
        map.put("key2", "key2Value");
        map.put("key3", "key3Value");
        when(msc.getAllParams()).thenReturn(map);
        String message = MessageMarshallerUtils.getMessage(roaRequest, MessageFormat.json);
        assertNotNull(message);
        assertTrue(message.indexOf("}") > -1);
        assertTrue(message.indexOf("{") > -1);
        assertTrue(message.indexOf("key1") > -1);
        assertTrue(message.indexOf("key1Value") > -1);

        message = MessageMarshallerUtils.getMessage(roaRequest, MessageFormat.xml);
        assertNotNull(message);
        assertTrue(message.indexOf("<") > -1);
        assertTrue(message.indexOf(">") > -1);
        assertTrue(message.indexOf("key1") > -1);
        assertTrue(message.indexOf("key1Value") > -1);
        System.out.println(message);
    }

    @Test
    public void testMarshallerROAResponse() throws Exception {
        SampleResponse response = new SampleResponse();
        response.setUserId("tom");
        response.setCreateTime("20120101");
        String message = MessageMarshallerUtils.getMessage(response, MessageFormat.json);
        assertTrue(message.indexOf("}") > -1);
        assertTrue(message.indexOf("{") > -1);
        assertTrue(message.indexOf("tom") > -1);
        assertTrue(message.indexOf("20120101") > -1);

        System.out.println(message);
        message = MessageMarshallerUtils.getMessage(response, MessageFormat.xml);
        assertTrue(message.indexOf("<?xml") > -1);
        assertTrue(message.indexOf(">") > -1);
        assertTrue(message.indexOf("tom") > -1);
        assertTrue(message.indexOf("20120101") > -1);
        System.out.println(message);
    }
}

