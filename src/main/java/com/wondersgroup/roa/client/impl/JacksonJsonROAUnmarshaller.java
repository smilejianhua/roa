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
package com.wondersgroup.roa.client.impl;

import com.wondersgroup.roa.client.ROAUnmarshaller;
import com.wondersgroup.roa.context.ROAException;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

import java.io.IOException;

public class JacksonJsonROAUnmarshaller implements ROAUnmarshaller {

	private static ObjectMapper objectMapper;

	@Override
	public <T> T unmarshaller(String content, Class<T> objectType) {
		try {
			return getObjectMapper().readValue(content, objectType);
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
					.withAnnotationIntrospector(introspector);
			objectMapper.setSerializationConfig(serializationConfig);
			this.objectMapper = objectMapper;
		}
		return this.objectMapper;
	}
}
