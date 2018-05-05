package org.xixi.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
@EnableScheduling
@ComponentScan(basePackages = {
	"org.xixi"
})
public class WebConfig extends WebMvcConfigurationSupport {
private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);
	
	/**
	 * 路径匹配参数配置
	 */
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		logger.info("configurePathMatch @PathVariable实现");
		configurer.setUseSuffixPatternMatch(false);
	}
	
	@Bean
	public HandlerAdapter servletHandlerAdapter() {
		logger.info("HandlerAdapter 定位请求处理器");
		return new SimpleServletHandlerAdapter();
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		logger.info("LocaleChangeInterceptor 动态变更local");
		return new LocaleChangeInterceptor();
	}

	@Bean(name = "localeResolver")
	public CookieLocaleResolver cookieLocaleResolver() {
		logger.info("CookieLocaleResolver 客户端指定local");
		return new CookieLocaleResolver();
	}

	@Bean
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		logger.info("RequestMappingHandlerMapping");
		return super.requestMappingHandlerMapping();
	}

	@Bean
	public MyInterceptor initializingInterceptor() {
		logger.info("MyInterceptor 自定义拦截器");
		return new MyInterceptor();
	}

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		logger.info("addInterceptors start 添加拦截器开始");
		registry.addInterceptor(localeChangeInterceptor());
		registry.addInterceptor(initializingInterceptor()); // 自定义拦截器
		logger.info("addInterceptors end 添加拦截器结束");
	}
	
	@Bean  
    public HandlerMapping resourceHandlerMapping() {  
        logger.info("HandlerMapping");  
        return super.resourceHandlerMapping();  
    } 
	
	@Override  
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {  
        logger.info("addResourceHandlers 添加静态资源");  
        registry.addResourceHandler("/images/**").addResourceLocations("/images/");  
        registry.addResourceHandler("/plugins/**").addResourceLocations("/plugins/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/json/**").addResourceLocations("/json/");
        registry.addResourceHandler("/layui/**").addResourceLocations("/layui/");
        registry.addResourceHandler("/page/**").addResourceLocations("/page/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("/favicon.ico");
    }  
	
	@Bean  
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {  
        logger.info("RequestMappingHandlerAdapter");  
        return super.requestMappingHandlerAdapter();  
    }
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver bean = new CommonsMultipartResolver();
		bean.setMaxUploadSize(102400000l);
		return bean;
	}
	
}