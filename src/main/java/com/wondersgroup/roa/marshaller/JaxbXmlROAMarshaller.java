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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 将对象流化成XML，每个类型对应一个{@link JAXBContext}，{@link JAXBContext} 是线程安全的，但是
 * {@link Marshaller}是非线程安全的，因此需要每次创建一个。
 * 
 * @author Jacky.Li
 */
public class JaxbXmlROAMarshaller implements ROAMarshaller {

	private static Map<Class, JAXBContext> jaxbContextHashMap = new ConcurrentHashMap<Class, JAXBContext>();

	public void marshaller(Object object, OutputStream outputStream) {
		try {
			Marshaller m = buildMarshaller(object.getClass());
			m.marshal(object, outputStream);
		}
		catch (JAXBException e) {
			throw new ROAException(e);
		}
	}

	private Marshaller buildMarshaller(Class<?> objectType) throws JAXBException {
		if (!jaxbContextHashMap.containsKey(objectType)) {
			JAXBContext context = JAXBContext.newInstance(objectType);
			jaxbContextHashMap.put(objectType, context);
		}
		JAXBContext context = jaxbContextHashMap.get(objectType);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
		return marshaller;
	}
}
