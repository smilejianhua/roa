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
package com.wondersgroup.roa.marshaller;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.wondersgroup.roa.MessageFormat;
import com.wondersgroup.roa.context.ROAException;
import com.wondersgroup.roa.context.ROAMarshaller;
import com.wondersgroup.roa.context.ROARequest;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * 对请求响应的对象转成相应的报文。
 * 
 * @author Jacky.Li
 */
public class MessageMarshallerUtils {

	protected static final Logger logger = LoggerFactory.getLogger(MessageMarshallerUtils.class);

	private static final String UTF_8 = "utf-8";

	private static ObjectMapper jsonObjectMapper = new ObjectMapper();

	private static ROAMarshaller xmlROAResponseMarshaller = new JaxbXmlROAMarshaller();

	static {
		SerializationConfig serializationConfig = jsonObjectMapper.getSerializationConfig();
		serializationConfig = serializationConfig.without(SerializationConfig.Feature.WRAP_ROOT_VALUE).with(
				SerializationConfig.Feature.INDENT_OUTPUT);
	}

	private static XmlMapper xmlObjectMapper = new XmlMapper();

	static {
		xmlObjectMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, false);
		xmlObjectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	}

	/**
	 * 将请求对象转换为String
	 * 
	 * @param request
	 * @param format
	 * @return
	 */
	public static String getMessage(ROARequest request, MessageFormat format) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
			if (format == MessageFormat.json) {
				if (request.getROARequestContext() != null) {
					jsonObjectMapper.writeValue(bos, request.getROARequestContext().getAllParams());
				}
				else {
					return "";
				}
			}
			else {
				if (request.getROARequestContext() != null) {
					xmlObjectMapper.writeValue(bos, request.getROARequestContext().getAllParams());
				}
				else {
					return "";
				}
			}
			return bos.toString(UTF_8);
		}
		catch (Exception e) {
			throw new ROAException(e);
		}
	}

	/**
	 * 将请求对象转换为String
	 * 
	 * @param request
	 * @param format
	 * @return
	 */
	public static String asUrlString(ROARequest request) {
		Map<String, String> allParams = request.getROARequestContext().getAllParams();
		StringBuilder sb = new StringBuilder(256);
		boolean first = true;
		for (Map.Entry<String, String> entry : allParams.entrySet()) {
			if (!first) {
				sb.append("&");
			}
			first = false;
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(entry.getValue());
		}
		return sb.toString();
	}

	/**
	 * 将{@link ROARequest}转换为字符串
	 * 
	 * @param object
	 * @param format
	 * @return
	 */
	public static String getMessage(Object object, MessageFormat format) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
		try {
			if (format == MessageFormat.json) {
				JsonGenerator jsonGenerator = jsonObjectMapper.getJsonFactory().createJsonGenerator(bos,
						JsonEncoding.UTF8);
				jsonObjectMapper.writeValue(jsonGenerator, object);
			}
			else {
				xmlROAResponseMarshaller.marshaller(object, bos);
			}
			return bos.toString(UTF_8);
		}
		catch (Throwable e) {
			throw new ROAException(e);
		}
		finally {
			try {
				bos.close();
			}
			catch (IOException ee) {
				logger.error("消息转换异常", ee);
			}
		}
	}

}
