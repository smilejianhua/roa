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
package com.wondersgroup.roa.annotation;

import java.lang.annotation.*;

/**
 * 默认情况下，请求对象的所有field都会作为请求参数提交，如果希望某个field不作为参数提交，可以打上{@Temporary}注解，如下所示：
 * <pre class="code">
 * <DIV>public class MyROARequest implements ROARequest{
 * <DIV></DIV>
 * <DIV>private String field1;</DIV>
 * <DIV>@Temporary</DIV>
 * <DIV>private String field2;</DIV>}
 * </pre>
 * 
 * @author Jacky.Li
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Temporary {

}

