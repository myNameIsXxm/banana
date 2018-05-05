package org.xixi.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.xixi.core.CommConfig;


@Configuration
public class ViewConfig extends WebMvcConfigurerAdapter{
	private static final Logger logger = LoggerFactory.getLogger(ViewConfig.class);
	
	/**
	 * ViewResolver 视图解析器:把一个逻辑上的视图名称解析为一个真正的视图(View)
	 */
	@Bean
	public ViewResolver getViewResolver(ContentNegotiationManager manager){
		logger.info("ViewResolver");
		ContentNegotiatingViewResolver bean = new ContentNegotiatingViewResolver();
		bean.setContentNegotiationManager(manager);
		List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();
		viewResolvers.add(new BeanNameViewResolver());
		//viewResolvers.add(new XmlViewResolver());
		viewResolvers.add(new JsonViewResolver());
		/*
		 * InternalResourceViewResolver(内部资源视图解析器)是URLBasedViewResolver的子类,
		 * 它把Controller处理器方法返回的模型属性都存放到对应的request属性中,
		 * 然后通过RequestDispatcher在服务器端把请求forword重定向到目标URL
		 */
		InternalResourceViewResolver jspViewResolver = new InternalResourceViewResolver();
		jspViewResolver.setPrefix("/jsp/");
		jspViewResolver.setSuffix(".jsp");
		viewResolvers.add(jspViewResolver);
		bean.setViewResolvers(viewResolvers);
		return bean;
	}
	
	@Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
        	.favorParameter(false) // 关闭参数支持
    		.favorPathExtension(false) // 关闭扩展名支持
    		.ignoreAcceptHeader(true) // 忽略对Accept Header的支持
    		.defaultContentType(MediaType.TEXT_HTML); // 默认展现形式
    }
	
}
