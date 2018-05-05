package org.xixi.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class ReturnAdvice {
	
	Logger logger = LoggerFactory.getLogger(ReturnAdvice.class);

	@AfterReturning(pointcut = "execution(* com.es.controller.*.*(..))", returning = "result")
	public void doAfterReturning(String result) {
		logger.info("·µ»Ø : "+result);
	}

}