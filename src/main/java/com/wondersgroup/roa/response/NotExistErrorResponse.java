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
package com.wondersgroup.roa.response;

import com.wondersgroup.roa.security.MainError;
import com.wondersgroup.roa.security.SubError;
import com.wondersgroup.roa.security.SubErrorType;
import com.wondersgroup.roa.security.SubErrors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Locale;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "error")
public class NotExistErrorResponse extends ErrorResponse {

    public static final String ISV = "isv.";
    public static final String NOT_EXIST_INVALID = "-not-exist:invalid-";

    //注意，这个不能删除，否则无法进行流化
    public NotExistErrorResponse() {
    }

    /**
     * 对象不存在的错误对象。当根据<code>queryFieldName</code>查询<code>objectName</code>时，查不到记录，则返回该错误对象。
     *
     * @param objectName     对象的名称
     * @param queryFieldName 查询字段的名称
     * @param locale         本地化对象
     * @param params         错误信息的参数，如错误消息的值为:use '{0}'({1})can't find '{2}' object ，则传入的参数为001时，错误消息格式化为：
     *                       can't find user by 001
     */
    public NotExistErrorResponse(String objectName, String queryFieldName, Object queryFieldValue, Locale locale) {
        MainError mainError = SubErrors.getMainError(SubErrorType.ISV_NOT_EXIST, locale);
        String subErrorCode = SubErrors.getSubErrorCode(SubErrorType.ISV_NOT_EXIST, objectName, queryFieldName);

        SubError subError = SubErrors.getSubError(subErrorCode, SubErrorType.ISV_NOT_EXIST.value(), locale,
                                                 queryFieldName, queryFieldValue,objectName);
        ArrayList<SubError> subErrors = new ArrayList<SubError>();
        subErrors.add(subError);

        setMainError(mainError);
        setSubErrors(subErrors);
    }
}
