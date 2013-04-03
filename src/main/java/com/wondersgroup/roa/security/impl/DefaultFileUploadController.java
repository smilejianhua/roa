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
package com.wondersgroup.roa.security.impl;

import java.util.ArrayList;
import java.util.List;

import com.wondersgroup.roa.security.FileUploadController;

/**
 * 1.如果maxSize为非正数，则表示不限制大小；
 * 2.如果allowAllTypes为true表示不限制文件类型；
 * 
 * @author Jacky.Li
 */
public class DefaultFileUploadController implements FileUploadController {

    private List<String> fileTypes;
    
    private int maxSize;

    private boolean allowAllTypes = false;

    public DefaultFileUploadController(int maxSize) {
        this.allowAllTypes = true;
        this.maxSize = maxSize;
    }

    public DefaultFileUploadController(List<String> fileTypes, int maxSize) {
        ArrayList<String> tempFileTypes = new ArrayList<String>(fileTypes.size());
        for (String fileType : fileTypes) {
            tempFileTypes.add(fileType.toLowerCase());
        }
        this.fileTypes = tempFileTypes;
        this.maxSize = maxSize;
    }

    @Override
    public boolean isAllowFileType(String fileType) {
        if(allowAllTypes){
            return true;
        }else{
            if(fileType == null){
                return false;
            }else{
                fileType = fileType.toLowerCase();
                return fileTypes.contains(fileType);
            }
        }
    }

    @Override
    public boolean isExceedMaxSize(int fileSize) {
        if(maxSize > 0){
            return fileSize > maxSize * 1024;
        }else{
            return false;
        }
    }
}

