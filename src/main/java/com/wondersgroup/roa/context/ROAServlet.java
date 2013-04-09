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

import com.wondersgroup.roa.Interceptor;
import com.wondersgroup.roa.security.ApiSecretManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * HttpServlet是ROA框架的总入口，提供了多个定制ROA框架的配置参数：
 * 
 * 1.ROA会自己扫描Spring容器并加载之{@link SessionChecker}、{@link ApiSecretManager}及
 * {@link Interceptor}的Bean。也可以通过 "sessionCheckerClassName"、"appSecretManagerClassName"
 * 和"interceptorClassNames"的Servlet参数指定实现类的类名。如果显式指定了Servlet
 * 参数，则ROA就不会扫描Spring容器中的Bean了。如果既没有使用Servlet参数指定，也没有在Spring容器中配置，则ROA使用
 * {@link DefaultSessionChecker}和{@link FileBaseApiSecretManager} 作为
 * {@link SessionChecker}和{@link ApiSecretManager}的实现类。
 * 
 * 2.可通过"errorResourceBaseName"指定错误资源文件的基名，默认为“i18n/roa/roaError”.
 * 
 * @author Jacky.Li
 */
public class ROAServlet extends HttpServlet {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private ServiceRouter serviceRouter;

	/**
	 * 将请求导向到ROA的框架中。
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		serviceRouter.service(req, resp);
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		ApplicationContext ctx = getApplicationContext(servletConfig);
		this.serviceRouter = ctx.getBean(ServiceRouter.class);
		if (this.serviceRouter == null) {
			logger.error("在Spring容器中未找到" + ServiceRouter.class.getName()
					+ "的Bean,请在Spring配置文件中通过<aop:annotation-driven/>安装ROA框架。");
		}
	}

	private ApplicationContext getApplicationContext(ServletConfig servletConfig) {
		return (ApplicationContext) servletConfig.getServletContext().getAttribute(
				WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	}
}
