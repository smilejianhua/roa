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

import java.lang.reflect.Method;
import java.util.List;


/**
 * 服务处理器的相关信息
 * 
 * @author Jacky.Li
 */
public class ServiceMethodHandler {

	// 处理器对象
	private Object handler;

	// 处理器的处理方法
	private Method handlerMethod;

	private ServiceMethodDefinition serviceMethodDefinition;

	// 处理方法的请求对象类
	private Class<? extends ROARequest> requestType = ROARequest.class;

	// 无需签名的字段列表
	private List<String> ignoreSignFieldNames;

	// 属性类型为FileItem的字段列表
	private List<String> uploadFileFieldNames;

	// 是否是实现类
	private boolean roaRequestImplType;

	public ServiceMethodHandler() {
	}

	public Object getHandler() {
		return handler;
	}

	public void setHandler(Object handler) {
		this.handler = handler;
	}

	public Method getHandlerMethod() {
		return handlerMethod;
	}

	public void setHandlerMethod(Method handlerMethod) {
		this.handlerMethod = handlerMethod;
	}

	public Class<? extends ROARequest> getRequestType() {
		return requestType;
	}

	public void setRequestType(Class<? extends ROARequest> requestType) {
		this.requestType = requestType;
	}

	public boolean isHandlerMethodWithParameter() {
		return this.requestType != null;
	}

	public void setIgnoreSignFieldNames(List<String> ignoreSignFieldNames) {
		this.ignoreSignFieldNames = ignoreSignFieldNames;
	}

	public List<String> getIgnoreSignFieldNames() {
		return ignoreSignFieldNames;
	}

	public ServiceMethodDefinition getServiceMethodDefinition() {
		return serviceMethodDefinition;
	}

	public void setServiceMethodDefinition(ServiceMethodDefinition serviceMethodDefinition) {
		this.serviceMethodDefinition = serviceMethodDefinition;
	}

	public static String methodWithVersion(String methodName, String version) {
		return methodName + "#" + version;
	}

	public boolean isROARequestImplType() {
		return roaRequestImplType;
	}

	public void setROARequestImplType(boolean roaRequestImplType) {
		this.roaRequestImplType = roaRequestImplType;
	}

	public List<String> getUploadFileFieldNames() {
		return uploadFileFieldNames;
	}

	public void setUploadFileFieldNames(List<String> uploadFileFieldNames) {
		this.uploadFileFieldNames = uploadFileFieldNames;
	}

	public boolean hasUploadFiles() {
		return uploadFileFieldNames != null && uploadFileFieldNames.size() > 0;
	}
}
