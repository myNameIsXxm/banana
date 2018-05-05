package org.xixi.core;


import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.xixi.aop.AopConfig;
import org.xixi.convert.ConvertConfig;
import org.xixi.exception.ExceptionConfig;
import org.xixi.view.ViewConfig;

public class AppInitializer implements WebApplicationInitializer {

	private static final Logger logger = LoggerFactory.getLogger(AppInitializer.class);
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		WebApplicationContext webContext = getWebContext();
		
		logger.info("contextLoaderListener �����ļ���������ʼ������ ROOT WebApplicationContext��");
		servletContext.addListener(new ContextLoaderListener(webContext));
		
		logger.info("dispatcherServlet ���ķַ�������ʼ������ SpringMVC WebApplicationContext��");
		DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true); // ��Ϊ���������������ã��ڷ���404��ʱ�򣬾Ͳ����׳��쳣�����Ƿ���һ��404״̬����Ӧ
		ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", dispatcherServlet);
		registration.setLoadOnStartup(1);
		registration.addMapping("/");
		
		logger.info("characterEncodingFilter �ַ��������");
		FilterRegistration.Dynamic characterEncodingFilter = servletContext
			.addFilter("characterEncodingFilter", new CharacterEncodingFilter());
		characterEncodingFilter.setInitParameter("encoding", "UTF-8");
		characterEncodingFilter.setInitParameter("ForceEncoding", "true");
		characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");
		
		/*logger.info("DruidStatView");
		ServletRegistration.Dynamic druidStat = servletContext
			.addServlet("DruidStatView", new StatViewServlet());
		druidStat.setInitParameter("resetEnable", "true"); // �������ͳ������
		druidStat.setInitParameter("loginUsername", "admin");
		druidStat.setInitParameter("loginPassword", "123456");
		druidStat.addMapping("/druid/*");
		logger.info("url [druid/index.html] name [admin] pass [123456]");*/
		
		logger.info("requestContextListener ���������");
		servletContext.addListener(new RequestContextListener());
	}
	
	public static WebApplicationContext getWebContext(){
		AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
		webContext.register(
			WebConfig.class,
			ExceptionConfig.class,
			AopConfig.class,
			ViewConfig.class,
			ConvertConfig.class
		);
		return webContext;
	}
	
}