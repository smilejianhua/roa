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
package com.wondersgroup.roa.context;

import com.wondersgroup.roa.MessageFormat;
import com.wondersgroup.roa.context.ROAContext;
import com.wondersgroup.roa.context.impl.SimpleROARequestContext;
import com.wondersgroup.roa.context.model.Addresss;
import com.wondersgroup.roa.request.ROARequestMessageConverter;

import org.junit.Test;
import org.springframework.core.convert.TypeDescriptor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ROARequestMessageConverterTest {

    @Test
    public void testConvertOfXmlFormat() throws Exception {
        ROAContext roaContext = mock(ROAContext.class);
        SimpleROARequestContext context = new SimpleROARequestContext(roaContext);
        context.setMessageFormat(MessageFormat.xml);

        TypeDescriptor addrTypeDescriptor = TypeDescriptor.valueOf(Addresss.class);
        TypeDescriptor strTypeDescriptor = TypeDescriptor.valueOf(String.class);
        ROARequestMessageConverter converter = new ROARequestMessageConverter();
        String addressStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<address zoneCode=\"001\" doorCode=\"002\">\n" +
                "  <streets>\n" +
                "    <street no=\"001\" name=\"street1\"/>\n" +
                "    <street no=\"002\" name=\"street2\"/>\n" +
                "  </streets>\n" +
                "</address>";
        Object destObj = converter.convert(addressStr, strTypeDescriptor, addrTypeDescriptor);
        assertTrue(destObj instanceof Addresss);
        Addresss addresss = (Addresss) destObj;
        assertEquals(addresss.getZoneCode(), "001");
        assertEquals(addresss.getDoorCode(), "002");
        assertEquals(addresss.getStreets().size(), 2);
    }

    @Test
    public void testConvertOfJsonFormat() throws Exception {
        ROAContext roaContext = mock(ROAContext.class);
        SimpleROARequestContext context = new SimpleROARequestContext(roaContext);
        context.setMessageFormat(MessageFormat.json);

        TypeDescriptor addrTypeDescriptor = TypeDescriptor.valueOf(Addresss.class);
        TypeDescriptor strTypeDescriptor = TypeDescriptor.valueOf(String.class);
        ROARequestMessageConverter converter = new ROARequestMessageConverter();
        String addressStr = "{\"zoneCode\":\"001\",\"doorCode\":\"002\",\"streets\":[{\"no\":\"001\",\"name\":\"street1\"}]}";
        Object destObj = converter.convert(addressStr, strTypeDescriptor, addrTypeDescriptor);
        assertTrue(destObj instanceof Addresss);
        Addresss addresss = (Addresss) destObj;
        assertEquals(addresss.getZoneCode(), "001");
        assertEquals(addresss.getDoorCode(), "002");
        assertNotNull(addresss.getStreets());
        assertEquals(addresss.getStreets().size(), 1);
    }
}

