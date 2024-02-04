package com.example.utility;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

	private void log(Exception exception) {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		logger.error(exception.getMessage(), exception);
	}
	
//	@AfterThrowing(pointcut = "execution(* com.example.controller.*.*(..))", throwing = "exception")
//	public void logExceptionFromRepository(Exception exception) throws Exception {
//		log(exception);
//	}
//	@Before(pointcut = "execution(* com.example.controller.*.*(..))", value = "")
//	public void logExceptionFromController() {
//		if (exception.getMessage().contains("Service")) {
//			log(exception);
//		}
//	}
}
