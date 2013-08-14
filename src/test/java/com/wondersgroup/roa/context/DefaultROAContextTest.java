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

import com.wondersgroup.roa.AbstractROARequest;
import com.wondersgroup.roa.annotation.*;
import com.wondersgroup.roa.context.ROAContext;
import com.wondersgroup.roa.context.ServiceMethodDefinition;
import com.wondersgroup.roa.context.ServiceMethodHandler;
import com.wondersgroup.roa.context.impl.DefaultROAContext;
import com.wondersgroup.roa.context.model.ExtUploadFile;
import com.wondersgroup.roa.request.UploadFile;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultROAContextTest {

    @Test
    public void testWithoutGroupService() {
        ApplicationContext context = mock(ApplicationContext.class);
        when(context.getBeanNamesForType(Object.class)).thenReturn(new String[]{"service1"});
        Class withoutGroupServiceClass = WithoutGroupService.class;
        when(context.getType("service1")).thenReturn(withoutGroupServiceClass);

        ROAContext registryMethod = new DefaultROAContext(context);
        ServiceMethodHandler handler = registryMethod.getServiceMethodHandler("service.method1", "1.0");
        ServiceMethodDefinition definition = handler.getServiceMethodDefinition();
        assertNotNull(definition);
        assertEquals(definition.getMethod(), "service.method1");
        assertEquals(definition.getMethodTitle(), "测试方法1");
        assertEquals(definition.getMethodGroup(), "GROUP1");
        assertEquals(definition.getMethodGroupTitle(), "分组1");
//        assertEquals(definition.getTags(), new String[]{"TAG1", "TAG2"});
        assertEquals(definition.isIgnoreSign(), true);
        assertEquals(definition.isNeedInSession(), false);
        assertEquals(definition.getTimeout(), 100);
        assertEquals(definition.getVersion(), "1.0");
    }

    @Test
    public void testWithGroupService() {
        ApplicationContext context = mock(ApplicationContext.class);

        when(context.getBeanNamesForType(Object.class)).thenReturn(new String[]{"service1"});
        Class withGroupServiceClass = WithGroupService.class;
        when(context.getType("service1")).thenReturn(withGroupServiceClass);
        ROAContext roaContext = new DefaultROAContext(context);

        //method1:都在ServiceMethodGroup中定义，在ServiceMethod中直接采用
        ServiceMethodHandler handler = roaContext.getServiceMethodHandler("service.method1", "1.0");
        ServiceMethodDefinition definition = handler.getServiceMethodDefinition();
        assertNotNull(definition);
        assertEquals(definition.getMethod(), "service.method1");
        assertEquals(definition.getMethodTitle(), "测试方法1");
        assertEquals(definition.getMethodGroup(), "GROUP1");
        assertEquals(definition.getMethodGroupTitle(), "分组1");
//        assertEquals(definition.getTags(), new String[]{"TAG1", "TAG2"});
        assertEquals(definition.isIgnoreSign(), true);
        assertEquals(definition.isNeedInSession(), false);
        assertEquals(definition.getTimeout(), 100);
        assertEquals(definition.getVersion(), "1.0");

        //method2:在ServiceMethodGroup中定义，在ServiceMethod显式覆盖之
        ServiceMethodHandler handler2 = roaContext.getServiceMethodHandler("service.method2", "2.0");
        ServiceMethodDefinition definition2 = handler2.getServiceMethodDefinition();
        assertNotNull(definition2);
        assertEquals(definition2.getMethod(), "service.method2");
        assertEquals(definition2.getMethodTitle(), "测试方法2");
        assertEquals(definition2.getMethodGroup(), "GROUP2");
        assertEquals(definition2.getMethodGroupTitle(), "分组2");
//        assertEquals(definition2.getTags(), new String[]{"TAG11", "TAG21"});
        assertEquals(definition2.isIgnoreSign(), false);
        assertEquals(definition2.isNeedInSession(), true);
        assertEquals(definition2.getTimeout(), 200);
        assertEquals(definition2.getVersion(), "2.0");
    }

    @Test
    public void testIngoreSignField() {
        ApplicationContext context = mock(ApplicationContext.class);
        when(context.getBeanNamesForType(Object.class)).thenReturn(new String[]{"method1"});
        Class serviceClass = IgnoreSignROARequestService.class;
        when(context.getType("method1")).thenReturn(serviceClass);
        ROAContext roaContext = new DefaultROAContext(context);
        ServiceMethodHandler method1 = roaContext.getServiceMethodHandler("method1", "1.0");
        List<String> ignoreSignFieldNames = method1.getIgnoreSignFieldNames();
        assertNotNull(ignoreSignFieldNames);
        assertEquals(ignoreSignFieldNames.size(), 4);
        assertTrue(ignoreSignFieldNames.contains("field1"));
        assertTrue(ignoreSignFieldNames.contains("field3"));
        assertTrue(ignoreSignFieldNames.contains("sign"));
    }

    public class IgnoreSignROARequestService {
        @ServiceMethod(method = "method1", version = "1.0")
        public Object method1(FooROARequest request) {
            return null;
        }
    }

    public class FooROARequest extends AbstractROARequest {

        @IgnoreSign
        private String field1;

        private String field2;

        @IgnoreSign
        private int field3;

        private int field4;

        public String getField1() {
            return field1;
        }

        public void setField1(String field1) {
            this.field1 = field1;
        }

        public String getField2() {
            return field2;
        }

        public void setField2(String field2) {
            this.field2 = field2;
        }

        public int getField3() {
            return field3;
        }

        public void setField3(int field3) {
            this.field3 = field3;
        }

        public int getField4() {
            return field4;
        }

        public void setField4(int field4) {
            this.field4 = field4;
        }
    }

    public class WithoutGroupService {
        @ServiceMethod(method = "service.method1", title = "测试方法1", group = "GROUP1", groupTitle = "分组1",
                tags = {"TAG1", "TAG2"}, ignoreSign = IgnoreSignType.YES,
                needInSession = NeedInSessionType.NO, timeout = 100, version = "1.0")
        public Object service1() {
             return null;
        }
    }

    @ServiceMethodBean(group = "GROUP1", groupTitle = "分组1",
            tags = {"TAG1", "TAG2"}, ignoreSign = IgnoreSignType.YES,
            needInSession = NeedInSessionType.NO, timeout = 100, version = "1.0")
    public class WithGroupService {

        @ServiceMethod(method = "service.method1", version = "1.0", title = "测试方法1")
        public Object service1() {
            return null;
        }

        @ServiceMethod(method = "service.method2", title = "测试方法2", group = "GROUP2", groupTitle = "分组2",
                tags = {"TAG11", "TAG21"}, ignoreSign = IgnoreSignType.NO,
                needInSession = NeedInSessionType.YES, timeout = 200, version = "2.0")
        public Object service2() {
            return null;
        }
    }

    @Test
    public void annotationTest(){
        IgnoreSign annotation = AnnotationUtils.findAnnotation(UploadFile.class, IgnoreSign.class);
        assertNotNull(annotation);
        annotation = AnnotationUtils.findAnnotation(ExtUploadFile.class, IgnoreSign.class);
        assertNotNull(annotation);
    }

}

