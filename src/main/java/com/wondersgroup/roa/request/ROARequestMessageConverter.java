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

import com.wondersgroup.roa.MessageFormat;
import com.wondersgroup.roa.context.ROAException;
import com.wondersgroup.roa.context.ROARequestParseException;
import com.wondersgroup.roa.context.impl.SimpleROARequestContext;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.util.Assert;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.StringReader;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 将参数中的XML或JSON转换为对象的属性对象
 * 
 * @author Jacky.Li
 */
public class ROARequestMessageConverter implements ConditionalGenericConverter {

	private static final ConcurrentMap<Class, JAXBContext> jaxbContexts = new ConcurrentHashMap<Class, JAXBContext>();

	private static final ObjectMapper objectMapper = new ObjectMapper();

	static {
		AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
		SerializationConfig serializationConfig = objectMapper.getSerializationConfig();
		serializationConfig = serializationConfig.without(SerializationConfig.Feature.WRAP_ROOT_VALUE)
				.withAnnotationIntrospector(introspector);
		objectMapper.setSerializationConfig(serializationConfig);
	}

	/**
	 * 如果目标属性类有标注JAXB的注解，则使用该转换器
	 * 
	 * @param sourceType
	 * @param targetType
	 * @return
	 */
	public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
		Class clazz = targetType.getObjectType();
		return clazz.isAnnotationPresent(XmlRootElement.class) || clazz.isAnnotationPresent(XmlType.class);
	}

	public Set<ConvertiblePair> getConvertibleTypes() {
		return Collections.singleton(new ConvertiblePair(String.class, Object.class));
	}

	public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		try {
			if (SimpleROARequestContext.messageFormat.get() == MessageFormat.json) {// 输入格式为JSON
				JsonParser jsonParser = objectMapper.getJsonFactory().createJsonParser((String) source);
				return jsonParser.readValueAs(targetType.getObjectType());
			}
			else {
				Unmarshaller unmarshaller = createUnmarshaller(targetType.getObjectType());
				StringReader reader = new StringReader((String) source);
				return unmarshaller.unmarshal(reader);
			}
		}
		catch (Exception e) {
			throw new ROARequestParseException((String) source, e);
		}
	}

	private Unmarshaller createUnmarshaller(Class clazz) throws JAXBException {
		try {
			JAXBContext jaxbContext = getJaxbContext(clazz);
			return jaxbContext.createUnmarshaller();
		}
		catch (JAXBException ex) {
			throw new ROAException("Could not create Unmarshaller for class [" + clazz + "]: " + ex.getMessage(), ex);
		}
	}

	private JAXBContext getJaxbContext(Class clazz) {
		Assert.notNull(clazz, "'clazz' must not be null");
		JAXBContext jaxbContext = jaxbContexts.get(clazz);
		if (jaxbContext == null) {
			try {
				jaxbContext = JAXBContext.newInstance(clazz);
				jaxbContexts.putIfAbsent(clazz, jaxbContext);
			}
			catch (JAXBException ex) {
				throw new HttpMessageConversionException("Could not instantiate JAXBContext for class [" + clazz
						+ "]: " + ex.getMessage(), ex);
			}
		}
		return jaxbContext;
	}

}
