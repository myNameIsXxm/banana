package org.xixi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class ExceptionConfig {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionConfig.class);

	@Bean
	public AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor(){
		logger.info("AutowiredAnnotationBeanPostProcessor");
		return new AutowiredAnnotationBeanPostProcessor();
	}
	
	
	/**
	 * ---> ÒÆ²½ ConvertConfig
	 */
	/*@Bean
	public GlobalExceptionResolver simpleMappingExceptionResolver(){
		logger.info("SimpleMappingExceptionResolver");
		GlobalExceptionResolver bean = new GlobalExceptionResolver();
		//bean.setDefaultErrorView("redirect:/main.html?d="+new Date().getTime());
		Properties mappings = new Properties();
		mappings.put("com.es.exception.AuthorizationException", "redirect:/welcome.html?d="+new Date().getTime());
		bean.setExceptionMappings(mappings);
		return bean;
	}*/
	
	
}
