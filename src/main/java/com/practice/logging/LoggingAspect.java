package com.practice.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    @Before(" execution(* com.practice.*.*.*(..))")
    public void beforeAdvice(JoinPoint point) {
        logger.info("INFO: {} ", point.getSignature().getName());
    }
    @After(" execution(* com.practice.*.*.*(..))")
    public void afterAdvice(JoinPoint point) {
        logger.info("INFO: {} ", point.getSignature().getName());
    }
    @AfterReturning(pointcut="execution(* com.practice.*.*.*(..))",returning="result")
    public void afterReturning(JoinPoint point,Object result) {
        logger.debug("DEBUG: {} Return Value: {} ", point.getSignature().getName(), result);
    }
    @AfterThrowing(pointcut="execution(* com.practice.*.*.*(..))",throwing="error")
    public void afterThrowing(JoinPoint point,Throwable error) {
        logger.error("Error: {}  threw exception : {} ", point.getSignature().getName(), error);
    }
}
