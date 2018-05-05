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
		
		logger.info("contextLoaderListener 上下文加载器（初始化容器 ROOT WebApplicationContext）");
		servletContext.addListener(new ContextLoaderListener(webContext));
		
		logger.info("dispatcherServlet 核心分发器（初始化容器 SpringMVC WebApplicationContext）");
		DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true); // 因为如果不加上这个配置，在发生404的时候，就不会抛出异常，而是返回一个404状态的响应
		ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", dispatcherServlet);
		registration.setLoadOnStartup(1);
		registration.addMapping("/");
		
		logger.info("characterEncodingFilter 字符编码过滤");
		FilterRegistration.Dynamic characterEncodingFilter = servletContext
			.addFilter("characterEncodingFilter", new CharacterEncodingFilter());
		characterEncodingFilter.setInitParameter("encoding", "UTF-8");
		characterEncodingFilter.setInitParameter("ForceEncoding", "true");
		characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");
		
		/*logger.info("DruidStatView");
		ServletRegistration.Dynamic druidStat = servletContext
			.addServlet("DruidStatView", new StatViewServlet());
		druidStat.setInitParameter("resetEnable", "true"); // 允许清空统计数据
		druidStat.setInitParameter("loginUsername", "admin");
		druidStat.setInitParameter("loginPassword", "123456");
		druidStat.addMapping("/druid/*");
		logger.info("url [druid/index.html] name [admin] pass [123456]");*/
		
		logger.info("requestContextListener 作用域监听");
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