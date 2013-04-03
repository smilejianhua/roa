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

import static org.junit.Assert.*;

import com.wondersgroup.roa.context.model.CreateUserRequest;
import com.wondersgroup.roa.request.ROARequestMessageConverter;

import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.ServletRequestDataBinder;

import java.util.HashSet;
import java.util.Set;


public class FormattingConversionServiceTest {

    @Test
    public void testDoBind() {
        FormattingConversionServiceFactoryBean serviceFactoryBean = new FormattingConversionServiceFactoryBean();
        Set<Object> converters = new HashSet<Object>();
        converters.add(new ROARequestMessageConverter());
        serviceFactoryBean.setConverters(converters);

        serviceFactoryBean.afterPropertiesSet();
        ConversionService conversionService = serviceFactoryBean.getObject();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("userName", "tom");
        request.setParameter("address",
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<address zoneCode=\"1\" doorCode=\"002\">\n" +
                        "  <streets>\n" +
                        "    <street no=\"001\" name=\"street1\"/>\n" +
                        "    <street no=\"002\" name=\"street2\"/>\n" +
                        "  </streets>\n" +
                        "</address>");

        CreateUserRequest bindObject = BeanUtils.instantiateClass(CreateUserRequest.class);
        ServletRequestDataBinder dataBinder = new ServletRequestDataBinder(bindObject, "bindObject");
        dataBinder.setConversionService(conversionService);
        dataBinder.setValidator(getValidator());
        dataBinder.bind(request);
        dataBinder.validate();

        assertTrue(dataBinder.getBindingResult().hasErrors());
        assertEquals(dataBinder.getBindingResult().getErrorCount(), 2);
        CreateUserRequest createUserRequest = (CreateUserRequest) dataBinder.getBindingResult().getTarget();
        assertNotNull(createUserRequest.getAddress());
        assertNotNull(createUserRequest.getAddress().getStreets());
        assertTrue(createUserRequest.getAddress().getStreets().size() > 0);
    }

    private Validator getValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }
}

