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
import com.wondersgroup.roa.config.SystemParameterNames;
import com.wondersgroup.roa.context.ROAContext;
import com.wondersgroup.roa.context.ServiceMethodHandler;
import com.wondersgroup.roa.context.impl.ServletRequestContextBuilder;
import com.wondersgroup.roa.context.impl.SimpleROARequestContext;
import com.wondersgroup.roa.session.SessionManager;

import org.junit.Test;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Locale;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServletRequestContextBuilderTest {

    @Test
    public void testIpParsed() {
        FormattingConversionService conversionService = mock(FormattingConversionService.class);
        SessionManager sessionManager = mock(SessionManager.class);
        ServletRequestContextBuilder requestContextBuilder = new ServletRequestContextBuilder(conversionService, sessionManager);
        ROAContext roaContext = mock(ROAContext.class);

        //构造HttpServletRequest
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.setRemoteAddr("1.1.1.1");

        //创建SimpleRequestContext
        SimpleROARequestContext requestContext = requestContextBuilder.buildBySysParams(roaContext, servletRequest);
        assertEquals(requestContext.getIp(), "1.1.1.1");

        servletRequest.setRemoteAddr("1.1.1.1");
        servletRequest.addHeader(ServletRequestContextBuilder.X_FORWARDED_FOR, "null,2.2.2.2,3.3.3.3");
        requestContext = requestContextBuilder.buildBySysParams(roaContext, servletRequest);
        assertEquals(requestContext.getIp(), "2.2.2.2");

        servletRequest.addHeader(ServletRequestContextBuilder.X_REAL_IP, "5.5.5.5");
        requestContext = requestContextBuilder.buildBySysParams(roaContext, servletRequest);
        assertEquals(requestContext.getIp(), "5.5.5.5");
    }

    /**
     * 正常情况下的系统参数绑定
     *
     * @throws Exception
     */
    @Test
    public void testBuildBySysParams1() throws Exception {
        FormattingConversionService conversionService = mock(FormattingConversionService.class);
        SessionManager sessionManager = mock(SessionManager.class);
        ServletRequestContextBuilder requestContextBuilder = new ServletRequestContextBuilder(conversionService, sessionManager);

        ROAContext roaContext = mock(ROAContext.class);
        ServiceMethodHandler methodHandler = mock(ServiceMethodHandler.class);
        when(roaContext.getServiceMethodHandler("method1", "3.0")).thenReturn(methodHandler);

        //构造HttpServletRequest
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();

        servletRequest.setParameter(SystemParameterNames.getAppKey(), "appKey1");
        servletRequest.setParameter(SystemParameterNames.getSessionId(), "sessionId1");
        servletRequest.setParameter(SystemParameterNames.getMethod(), "method1");
        servletRequest.setParameter(SystemParameterNames.getVersion(), "3.0");
        servletRequest.setParameter(SystemParameterNames.getLocale(), "en_UK");
        servletRequest.setParameter(SystemParameterNames.getFormat(), "xml");
        servletRequest.setParameter(SystemParameterNames.getSign(), "sign1");
        servletRequest.setParameter("param1", "value1");
        servletRequest.setParameter("param2", "value2");
        servletRequest.setParameter("param3", "value3");

        //创建SimpleRequestContext
        SimpleROARequestContext requestContext = requestContextBuilder.buildBySysParams(roaContext, servletRequest);

        assertEquals(requestContext.getAllParams().size(), 10);
        assertEquals(requestContext.getParamValue("param1"), "value1");
        assertEquals(requestContext.getRawRequestObject(), servletRequest);

        assertEquals(requestContext.getAppKey(), "appKey1");
        assertEquals(requestContext.getSessionId(), "sessionId1");
        assertEquals(requestContext.getMethod(), "method1");
        assertEquals(requestContext.getVersion(), "3.0");
        assertEquals(requestContext.getLocale(), new Locale("zh", "CN"));
        assertEquals(requestContext.getFormat(), "xml");
        assertEquals(requestContext.getMessageFormat(), MessageFormat.xml);
        assertEquals(requestContext.getSign(), "sign1");

        assertEquals(requestContext.getServiceMethodHandler(), methodHandler);
    }

    /**
     * 看错误的参数是否会被自动转为默认的
     *
     * @throws Exception
     */
    @Test
    public void testBuildBySysParams2() throws Exception {
        FormattingConversionService conversionService = mock(FormattingConversionService.class);
        SessionManager sessionManager = mock(SessionManager.class);
        ServletRequestContextBuilder requestContextBuilder = new ServletRequestContextBuilder(conversionService, sessionManager);
        ROAContext roaContext = mock(ROAContext.class);


        //构造HttpServletRequest
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.setParameter(SystemParameterNames.getLocale(), "xxx");
        servletRequest.setParameter(SystemParameterNames.getFormat(), "xxx");


        //创建SimpleRequestContext
        SimpleROARequestContext requestContext = requestContextBuilder.buildBySysParams(roaContext, servletRequest);
        assertEquals(requestContext.getLocale(), Locale.SIMPLIFIED_CHINESE);
        assertEquals(requestContext.getFormat(), "xxx");
        assertEquals(requestContext.getMessageFormat(), MessageFormat.xml);

    }

    /**
     * 非{@link javax.servlet.http.HttpServletRequest}
     *
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testBuildBySysParams3() throws Exception {
        FormattingConversionService conversionService = mock(FormattingConversionService.class);
        SessionManager sessionManager = mock(SessionManager.class);
        ServletRequestContextBuilder requestContextBuilder = new ServletRequestContextBuilder(conversionService, sessionManager);

        ROAContext roaContext = mock(ROAContext.class);
        ServiceMethodHandler methodHandler = mock(ServiceMethodHandler.class);
        when(roaContext.getServiceMethodHandler("method1", "3.0")).thenReturn(methodHandler);

        //创建SimpleRequestContext
        SimpleROARequestContext requestContext = requestContextBuilder.buildBySysParams(roaContext, new Object());
    }

    @Test
    public void testBindBusinessParams() throws Exception {

    }
}

