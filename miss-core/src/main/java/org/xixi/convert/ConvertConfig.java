package org.xixi.convert;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.xixi.exception.SpringHandlerExceptionResolver;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@Configuration
public class ConvertConfig extends WebMvcConfigurationSupport{
	private static final Logger logger = LoggerFactory.getLogger(ConvertConfig.class);
	@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		logger.info("configureMessageConverters start");
		converters.add(stringHttpMessageConverter());
		converters.add(fastJsonHttpMessageConverter());
		logger.info("configureMessageConverters end");
        super.configureMessageConverters(converters);
    }
	
	@Bean(name="stringHttpMessageConverter")
	public StringHttpMessageConverter stringHttpMessageConverter(){
		logger.info("StringHttpMessageConverter");
		List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
		supportedMediaTypes.add(MediaType.TEXT_HTML);
		
		StringHttpMessageConverter bean = new StringHttpMessageConverter();
		bean.setSupportedMediaTypes(supportedMediaTypes);
		bean.setDefaultCharset(Charset.forName("UTF-8"));
		return bean;
	}
	
	@Bean
	public FastJsonConfig getFastJsonConfig(){
		FastJsonConfig config = new FastJsonConfig();
		config.setCharset(Charset.forName("UTF-8"));
		config.setDateFormat("yyyy-MM-dd");
		config.setSerializerFeatures(SerializerFeature.WriteNullListAsEmpty,SerializerFeature.QuoteFieldNames,SerializerFeature.WriteDateUseDateFormat);
		return config;
	}
	
	
	@Bean(name="fastJsonHttpMessageConverter")
	public FastJsonHttpMessageConverter fastJsonHttpMessageConverter(){
		logger.info("FastJsonHttpMessageConverter");
		List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
		FastJsonHttpMessageConverter bean = new FastJsonHttpMessageConverter();
		bean.setSupportedMediaTypes(supportedMediaTypes);
		bean.setFastJsonConfig(getFastJsonConfig());
		return bean;
	}
	
	@Bean
	public SpringHandlerExceptionResolver springHandlerExceptionResolver(){
		logger.info("SpringHandlerExceptionResolver");
		return new SpringHandlerExceptionResolver(getFastJsonConfig());
	}
}
