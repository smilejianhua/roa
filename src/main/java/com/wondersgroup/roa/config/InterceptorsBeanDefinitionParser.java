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
package com.wondersgroup.roa.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.parsing.CompositeComponentDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.List;

public class InterceptorsBeanDefinitionParser implements BeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		CompositeComponentDefinition compDefinition = new CompositeComponentDefinition(element.getTagName(),
				parserContext.extractSource(element));
		parserContext.pushContainingComponent(compDefinition);
		List<Element> interceptors = DomUtils.getChildElementsByTagName(element, new String[] { "bean", "ref" });

		for (Element interceptor : interceptors) {
			RootBeanDefinition interceptorHolderDef = new RootBeanDefinition(InterceptorHolder.class);
			interceptorHolderDef.setSource(parserContext.extractSource(interceptor));
			interceptorHolderDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);

			Object interceptorBean = parserContext.getDelegate().parsePropertySubElement(interceptor, null);
			interceptorHolderDef.getConstructorArgumentValues().addIndexedArgumentValue(0, interceptorBean);

			String beanName = parserContext.getReaderContext().registerWithGeneratedName(interceptorHolderDef);
			parserContext.registerComponent(new BeanComponentDefinition(interceptorHolderDef, beanName));
		}
		parserContext.popAndRegisterContainingComponent();
		return null;
	}
}
