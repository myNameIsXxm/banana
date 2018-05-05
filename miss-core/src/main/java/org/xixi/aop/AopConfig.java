package org.xixi.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;



@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class AopConfig {
	@Bean
	public TimeAdvice initTimeAdvice(){
		return new TimeAdvice();
	}
	
	/*@Bean
	public ExceptionAdvice initExceptionAdvice(){
		return new ExceptionAdvice();
	}*/
	
	@Bean
	public ReturnAdvice initReturnAdvice(){
		return new ReturnAdvice();
	}
}
