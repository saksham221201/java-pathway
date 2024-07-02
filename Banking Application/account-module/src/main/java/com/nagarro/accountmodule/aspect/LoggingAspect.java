package com.nagarro.accountmodule.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component //declared component just so it gets scanned
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Pointcut expression- to match all methods in AccountServiceImpl
    @Before("execution(* com.nagarro.accountmodule.service.impl.AccountServiceImpl.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        logger.info("Entering method: {}", joinPoint.getSignature().getName());
    }

    // Advice to log after returning from methods in AccountServiceImpl
    @AfterReturning(pointcut = "execution(* com.nagarro.accountmodule.service.impl.AccountServiceImpl.*(..))", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        logger.info("Exiting method: {} with result: {}", joinPoint.getSignature().getName(), result);
    }

    // Advice to log an exception thrown from methods in AccountServiceImpl
    @AfterThrowing(pointcut = "execution(* com.nagarro.accountmodule.service.impl.AccountServiceImpl.*(..))", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in method: {} with cause: {}", joinPoint.getSignature().getName(), exception.getMessage());
    }
}

