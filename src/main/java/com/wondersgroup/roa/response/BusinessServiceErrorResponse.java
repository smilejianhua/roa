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

import com.wondersgroup.roa.security.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Locale;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "error")
public class BusinessServiceErrorResponse extends ErrorResponse {

    private static final String ISV = "isv.";
    private static final String SERVICE_ERROR = "-service-error:";

    //注意，这个不能删除，否则无法进行流化
    public BusinessServiceErrorResponse() {
    }

    /**
     * 服务发生错误的错误响应，错误码的格式为：isv.***-service-error:###,假设
     * serviceName为book.upload，error_code为INVLIAD_USERNAME_OR_PASSWORD，则错误码会被格式化为：
     * isv.book-upload-service-error:INVLIAD_USERNAME_OR_PASSWORD
     *
     * @param serviceName 服务名，如book.upload,会被自动转换为book-upload
     * @param errorCode   错误的代码，如INVLIAD_USERNAME_OR_PASSWORD,在错误码的后面，一般为大写或数字。
     * @param locale      本地化对象
     * @param params      错误信息的参数，如错误消息的值为this is a {0} error，则传入的参数为big时，错误消息格式化为：
     *                    this is a big error
     */
    public BusinessServiceErrorResponse(String serviceName, String errorCode, Locale locale, Object... params) {
        MainError mainError = MainErrors.getError(MainErrorType.BUSINESS_LOGIC_ERROR,locale);

        serviceName = transform(serviceName);
        String subErrorCode = ISV + serviceName + SERVICE_ERROR + errorCode;
        SubError subError = SubErrors.getSubError(subErrorCode, subErrorCode, locale, params);
        ArrayList<SubError> subErrors = new ArrayList<SubError>();
        subErrors.add(subError);

        setMainError(mainError);
        setSubErrors(subErrors);
    }
}

