package com.nagarro.transactionmodule.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("execution(* com.nagarro.transactionmodule.service.impl.TransactionServiceImpl.*(..))")
    public Object transactionServiceImplExecutionTime(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = point.proceed();
        long endTime = System.currentTimeMillis();
        logger.info("Class Name: {}. Method Name: {}. Time taken for Execution from transaction service is : {}ms", point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), endTime - startTime);
        return object;
    }

    @Around("execution(* com.nagarro.transactionmodule.service.impl.MailServiceImpl.*(..))")
    public Object MailServiceImplExecutionTime(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = point.proceed();
        long endTime = System.currentTimeMillis();
        logger.info("Class Name: {}. Method Name: {}. Time taken for Execution from mail service is : {}ms", point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), endTime - startTime);
        return object;
    }
}
