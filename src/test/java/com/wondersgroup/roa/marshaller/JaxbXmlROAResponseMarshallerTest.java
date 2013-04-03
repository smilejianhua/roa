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

import com.wondersgroup.roa.marshaller.JaxbXmlROAMarshaller;

import java.io.ByteArrayOutputStream;

import org.junit.Test;

public class JaxbXmlROAResponseMarshallerTest {

    @Test
    public void buildMarshaller() throws Throwable {
        JaxbXmlROAMarshaller marshaller = new JaxbXmlROAMarshaller();
        marshaller.marshaller(new SampleResponse(), new ByteArrayOutputStream(1024));
    }
}

