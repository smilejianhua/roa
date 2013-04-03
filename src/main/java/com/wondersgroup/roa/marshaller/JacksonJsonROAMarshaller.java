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

import com.wondersgroup.roa.context.ROAException;
import com.wondersgroup.roa.context.ROAMarshaller;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 将{@link com.wondersgroup.roa.ROAResponse}流化成JSON。 {@link ObjectMapper}是线程安全的。
 * 
 * @author Jacky.Li
 */
public class JacksonJsonROAMarshaller implements ROAMarshaller {

	private static ObjectMapper objectMapper;

	public void marshaller(Object object, OutputStream outputStream) {
		try {
			JsonGenerator jsonGenerator = getObjectMapper().getJsonFactory().createJsonGenerator(outputStream,
					JsonEncoding.UTF8);
			getObjectMapper().writeValue(jsonGenerator, object);
		}
		catch (IOException e) {
			throw new ROAException(e);
		}
	}

	private ObjectMapper getObjectMapper() throws IOException {
		if (this.objectMapper == null) {
			ObjectMapper objectMapper = new ObjectMapper();
			AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
			SerializationConfig serializationConfig = objectMapper.getSerializationConfig();
			serializationConfig = serializationConfig.without(SerializationConfig.Feature.WRAP_ROOT_VALUE)
					.with(SerializationConfig.Feature.INDENT_OUTPUT).withAnnotationIntrospector(introspector);
			objectMapper.setSerializationConfig(serializationConfig);
			this.objectMapper = objectMapper;
		}
		return this.objectMapper;
	}
}
