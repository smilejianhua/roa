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
package com.wondersgroup.roa.request;

/**
 * 将以BASE64位编码字符串转换为字节数组的{@link UploadFile}对象
 * 
 * @author Jacky.Li
 */
public class UploadFileConverter implements ROAConverter<String, UploadFile> {

    @Override
    public UploadFile convert(String source) {
        String fileType = UploadFileUtils.getFileType(source);
        byte[] contentBytes = UploadFileUtils.decode(source);
        return new UploadFile(fileType, contentBytes);
    }

    @Override
    public String unconvert(UploadFile target) {
        return UploadFileUtils.encode(target);
    }

    @Override
    public Class<String> getSourceClass() {
        return String.class;
    }

    @Override
    public Class<UploadFile> getTargetClass() {
        return UploadFile.class;
    }
}

