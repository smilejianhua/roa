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
package com.wondersgroup.roa.security;

public interface FileUploadController {

    /**
     * 上传文件的类型是否是允许
     * @param fileType
     * @return
     */
    public boolean isAllowFileType(String fileType);

    /**
     * 是否超过了上传大小的限制
     * @param fileSize
     * @return
     */
    public boolean isExceedMaxSize(int fileSize);
}

