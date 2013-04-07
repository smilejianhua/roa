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
package com.wondersgroup.roa.context.impl;

import com.wondersgroup.roa.*;
import com.wondersgroup.roa.annotation.HttpAction;
import com.wondersgroup.roa.context.ROAContext;
import com.wondersgroup.roa.context.ROARequest;
import com.wondersgroup.roa.context.ROARequestContext;
import com.wondersgroup.roa.context.ServiceMethodDefinition;
import com.wondersgroup.roa.context.ServiceMethodHandler;
import com.wondersgroup.roa.security.MainError;
import com.wondersgroup.roa.session.Session;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SimpleROARequestContext implements ROARequestContext {

	public static final String SPRING_VALIDATE_ERROR_ATTRNAME = "$SPRING_VALIDATE_ERROR_ATTRNAME";

	private ROAContext roaContext;

	private String method;

	private String version;

	private String apiKey;

	private String sessionId;

	private Locale locale;

	private String format;

	public static ThreadLocal<MessageFormat> messageFormat = new ThreadLocal<MessageFormat>();

	private String sign;

	private Map<String, Object> attributes = new HashMap<String, Object>();

	private ServiceMethodHandler serviceMethodHandler;

	private MainError mainError;

	private Object roaResponse;

	private ROARequest roaRequest;

	private long serviceBeginTime = -1;

	private long serviceEndTime = -1;

	private String ip;

	private HttpAction httpAction;

	private Object rawRequestObject;

	private Map<String, String> allParams;

	private String requestId = ROAUtils.getUUID();

	@Override
	public long getServiceBeginTime() {
		return this.serviceBeginTime;
	}

	@Override
	public long getServiceEndTime() {
		return this.serviceEndTime;
	}

	@Override
	public void setServiceBeginTime(long serviceBeginTime) {
		this.serviceBeginTime = serviceBeginTime;
	}

	@Override
	public void setServiceEndTime(long serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}

	@Override
	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@Override
	public Object getRawRequestObject() {
		return this.rawRequestObject;
	}

	public void setRawRequestObject(Object rawRequestObject) {
		this.rawRequestObject = rawRequestObject;
	}

	public SimpleROARequestContext(ROAContext roaContext) {
		this.roaContext = roaContext;
		this.serviceBeginTime = System.currentTimeMillis();
	}

	@Override
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public ROAContext getROAContext() {
		return roaContext;
	}

	@Override
	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String getSessionId() {
		return this.sessionId;
	}

	@Override
	public Session getSession() {
		if (roaContext != null && roaContext.getSessionManager() != null && getSessionId() != null) {
			return roaContext.getSessionManager().getSession(getSessionId());
		}
		else {
			return null;
		}
	}

	@Override
	public void addSession(String sessionId, Session session) {
		if (roaContext != null && roaContext.getSessionManager() != null) {
			roaContext.getSessionManager().addSession(sessionId, session);
		}
	}

	@Override
	public void removeSession() {
		if (roaContext != null && roaContext.getSessionManager() != null) {
			roaContext.getSessionManager().removeSession(getSessionId());
		}
	}

	@Override
	public Locale getLocale() {
		return this.locale;
	}

	public ServiceMethodHandler getServiceMethodHandler() {
		return this.serviceMethodHandler;
	}

	@Override
	public MessageFormat getMessageFormat() {
		return messageFormat.get();
	}

	@Override
	public Object getROAResponse() {
		return this.roaResponse;
	}

	@Override
	public ROARequest getROARequest() {
		return this.roaRequest;
	}

	@Override
	public void setROARequest(ROARequest roaRequest) {
		this.roaRequest = roaRequest;
	}

	public String getApiKey() {
		return this.apiKey;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public void setServiceMethodHandler(ServiceMethodHandler serviceMethodHandler) {
		this.serviceMethodHandler = serviceMethodHandler;
	}

	public void setMessageFormat(MessageFormat messageFormat) {
		this.messageFormat.set(messageFormat);
	}

	@Override
	public void setROAResponse(Object roaResponse) {
		this.roaResponse = roaResponse;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	@Override
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setMainError(MainError mainError) {
		this.mainError = mainError;
	}

	public MainError getMainError() {
		return this.mainError;
	}

	@Override
	public Object getAttribute(String name) {
		return this.attributes.get(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
		this.attributes.put(name, value);
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public boolean isSignEnable() {
		return roaContext.isSignEnable() && !getServiceMethodDefinition().isIgnoreSign();
	}

	@Override
	public ServiceMethodDefinition getServiceMethodDefinition() {
		return serviceMethodHandler.getServiceMethodDefinition();
	}

	@Override
	public Map<String, String> getAllParams() {
		return this.allParams;
	}

	public void setAllParams(Map<String, String> allParams) {
		this.allParams = allParams;
	}

	@Override
	public String getParamValue(String paramName) {
		if (allParams != null) {
			return allParams.get(paramName);
		}
		else {
			return null;
		}
	}

	public void setHttpAction(HttpAction httpAction) {
		this.httpAction = httpAction;
	}

	@Override
	public HttpAction getHttpAction() {
		return this.httpAction;
	}

	@Override
	public String getRequestId() {
		return this.requestId;
	}
}
