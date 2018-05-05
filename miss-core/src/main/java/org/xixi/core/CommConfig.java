package org.xixi.core;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.web.FilterChainProxy;

@Configuration
public class CommConfig {
	private static final Logger logger = LoggerFactory.getLogger(CommConfig.class);

	@Bean(name="dateFormat")
	public SimpleDateFormat getSimpleDateFormat(){
		logger.info("SimpleDateFormat");
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	/*@Bean(name="springSecurityFilterChain")
    public FilterChainProxy filterChainProxy(){
    	return new FilterChainProxy();
    }*/
	
}
