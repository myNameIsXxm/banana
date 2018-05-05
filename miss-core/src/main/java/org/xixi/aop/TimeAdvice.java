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
public class TimeAdvice {
	
	Logger logger = LoggerFactory.getLogger(TimeAdvice.class);

	@Around("execution(* com.es.controller.*.*(..))")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		long t1 = System.currentTimeMillis();
		Object o = pjp.proceed();
		long t2 = System.currentTimeMillis();
		logger.info("exec "+pjp.toShortString().replace("execution(", "")
				.replace("(..))", "")+" spend time "+(t2-t1)+"ms");
		//logger.info("-----------------«Î«ÛΩ· ¯-----------------");
		return o;
	}
}