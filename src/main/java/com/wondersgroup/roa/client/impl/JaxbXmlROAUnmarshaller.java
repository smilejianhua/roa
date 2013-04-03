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

import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JaxbXmlROAUnmarshaller implements ROAUnmarshaller {

	private static Map<Class, JAXBContext> jaxbContextHashMap = new ConcurrentHashMap<Class, JAXBContext>();

	@Override
	public <T> T unmarshaller(String content, Class<T> objectType) {
		try {
			Unmarshaller unmarshaller = buildUnmarshaller(objectType);
			StringReader reader = new StringReader(content);
			new InputSource(reader);
			return (T) unmarshaller.unmarshal(reader);
		}
		catch (JAXBException e) {
			throw new ROAException(e);
		}
	}

	private Unmarshaller buildUnmarshaller(Class<?> objectType) throws JAXBException {
		if (!jaxbContextHashMap.containsKey(objectType)) {
			JAXBContext context = JAXBContext.newInstance(objectType);
			jaxbContextHashMap.put(objectType, context);
		}
		JAXBContext context = jaxbContextHashMap.get(objectType);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		// unmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		// unmarshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
		return unmarshaller;
	}
}
