package com.nagarro.usermodule.aspect;

import com.nagarro.usermodule.util.JwtTokenUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component //declared component just so it gets scanned
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    // Pointcut expression- to match authenticateUser methods in AuthServiceImpl
    @Before("execution(* com.nagarro.usermodule.service.impl.AuthServiceImpl.authenticateUser(..))")
    public void logBeforeAuthenticate(JoinPoint joinPoint) {
        logger.info("Entering method: {}", joinPoint.getSignature().getName());
    }

    // Advice to log after returning user from authenticateUser methods in AuthServiceImpl
    @AfterReturning(pointcut = "execution(* com.nagarro.usermodule.service.impl.AuthServiceImpl.authenticateUser(..))", returning = "result")
    public void logAfterLogin(JoinPoint joinPoint, String result) {
        String userEmail = jwtTokenUtil.getEmailFromToken(result);
        logger.info("Exiting method: {} user is logged in : {}", joinPoint.getSignature().getName(), userEmail);
    }

    // Pointcut expression- to match all methods in UserServiceImpl
    @Before("execution(* com.nagarro.usermodule.service.impl.UserServiceImpl.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        logger.info("Entering user service method: {}", joinPoint.getSignature().getName());
    }

    // Advice to log after returning from methods in UserServiceImpl
    @AfterReturning(pointcut = "execution(* com.nagarro.usermodule.service.impl.UserServiceImpl.*(..))", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        logger.info("Exiting method: {} with result: {}", joinPoint.getSignature().getName(), result);
    }

    // Advice to log an exception thrown from methods in UserServiceImpl
    @AfterThrowing(pointcut = "execution(* com.nagarro.usermodule.service.impl.UserServiceImpl.*(..))", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in UserServiceImpl method: {} with cause: {}", joinPoint.getSignature().getName(), exception.getMessage());
    }

    // Advice to log an exception thrown from methods in UserServiceImpl
    @AfterThrowing(pointcut = "execution(* com.nagarro.usermodule.service.impl.AuthServiceImpl.*(..))", throwing = "exception")
    public void logAfterThrowingInAuthService(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in AuthServiceImpl method: {} with cause: {}", joinPoint.getSignature().getName(), exception.getMessage());
    }
}
