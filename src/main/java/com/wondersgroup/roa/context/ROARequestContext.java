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
import com.wondersgroup.roa.annotation.HttpAction;
import com.wondersgroup.roa.session.Session;

import java.util.Locale;
import java.util.Map;

/**
 * 接到服务请求后，将产生一个{@link ROARequestContext}上下文对象，它被本次请求直到返回响应的这个线程共享。
 * 
 * @author Jacky.Li
 *
 */
public interface ROARequestContext {

	/**
	 * 获取ROA的上下文
	 * 
	 * @return
	 */
	public ROAContext getROAContext();

	/**
	 * 获取服务的方法
	 * 
	 * @return
	 */
	public String getMethod();

	/**
	 * 获取服务的版本号
	 * 
	 * @return
	 */
	public String getVersion();

	/**
	 * 获取应用的appKey
	 * 
	 * @return
	 */
	public String getAppKey();

	/**
	 * 获取会话的ID
	 * 
	 * @return
	 */
	public String getSessionId();

	/**
	 * 获取请求所对应的会话
	 * 
	 * @return
	 */
	public Session getSession();

	/**
	 * 绑定一个会话
	 * 
	 * @param session
	 */
	public void addSession(String sessionId, Session session);

	/**
	 * 删除会话，删除{@link #getSessionId()}对应的Session
	 */
	public void removeSession();

	/**
	 * 获取报文格式化参数
	 * 
	 * @return
	 */
	public String getFormat();

	/**
	 * 获取响应的格式
	 * 
	 * @return
	 */
	public MessageFormat getMessageFormat();

	/**
	 * 获取本地化对象
	 * 
	 * @return
	 */
	public Locale getLocale();

	/**
	 * 获取签名
	 * 
	 * @return
	 */
	public String getSign();

	/**
	 * 获取客户端的IP
	 * 
	 * @return
	 */
	public String getIp();

	/**
	 * 获取请求的方法 如POST
	 */
	public HttpAction getHttpAction();

	/**
	 * 获取请求的原对象（如HttpServletRequest）
	 * 
	 * @return
	 */
	public Object getRawRequestObject();

	/**
	 * 设置服务开始时间
	 * 
	 * @param serviceBeginTime
	 */
	public void setServiceBeginTime(long serviceBeginTime);

	/**
	 * 获取服务开始时间，单位为毫秒，为-1表示无意义
	 * 
	 * @return
	 */
	public long getServiceBeginTime();

	/**
	 * 设置服务开始时间
	 * 
	 * @param serviceBeginTime
	 */
	public void setServiceEndTime(long serviceEndTime);

	/**
	 * 获取服务结束时间，单位为毫秒，为-1表示无意义
	 * 
	 * @return
	 */
	public long getServiceEndTime();

	/**
	 * 获取服务方法对应的ApiMethod对象信息
	 * 
	 * @return
	 */
	public ServiceMethodDefinition getServiceMethodDefinition();

	/**
	 * 获取服务的处理者
	 * 
	 * @return
	 */
	public ServiceMethodHandler getServiceMethodHandler();

	/**
	 * @param roaResponse
	 */
	public void setROAResponse(Object roaResponse);

	/**
	 * 返回响应对象
	 * 
	 * @return
	 */
	public Object getROAResponse();

	/**
	 * 设置{@link ROARequest}
	 * 
	 * @param roaRequest
	 */
	public void setROARequest(ROARequest roaRequest);

	/**
	 * 请求对象
	 * 
	 * @return
	 */
	public ROARequest getROARequest();

	/**
	 * 获取特定属性
	 * 
	 * @param name
	 * @return
	 */
	public Object getAttribute(String name);

	/**
	 * 设置属性的值
	 * 
	 * @param name
	 * @param value
	 */
	public void setAttribute(String name, Object value);

	/**
	 * 该方法是否开启签名的功能
	 * 
	 * @return
	 */
	public boolean isSignEnable();

	/**
	 * 获取请求参数列表
	 * 
	 * @return
	 */
	public Map<String, String> getAllParams();

	/**
	 * 获取请求参数值
	 * 
	 * @param paramName
	 * @return
	 */
	public String getParamValue(String paramName);

	/**
	 * 获取请求ID，是一个唯一的UUID，每次请求对应一个唯一的ID
	 * 
	 * @return
	 */
	public String getRequestId();
}
