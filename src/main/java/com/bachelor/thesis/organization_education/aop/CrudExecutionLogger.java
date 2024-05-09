package com.bachelor.thesis.organization_education.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class CrudExecutionLogger {
    private String className;
    private String methodName;
    private String methodArgs;

    @Pointcut("execution(* com.bachelor.thesis.organization_education.services.interfaces.university.*.*(..))")
    public void loggerPointcut() {}

    @Before("loggerPointcut()")
    public void beforeMethodExecution(JoinPoint joinPoint) {
        initData(joinPoint);
        log.info("Input to the {}.{}{} method", className, methodName, methodArgs);
    }

    @After("loggerPointcut()")
    public void afterMethodExecution(JoinPoint joinPoint) {
        initData(joinPoint);
        log.info("Exiting the {}.{}{} method", className, methodName, methodArgs);
    }

    @AfterThrowing(pointcut = "loggerPointcut()", throwing = "e")
    public void afterThrowingMethodExecution(JoinPoint joinPoint, Throwable e) {
        initData(joinPoint);
        log.warn("An exception was thrown in method: {}.{}{}", className, methodName, methodArgs);
        log.warn("Exception message: {}", e.getMessage());
    }

    private void initData(JoinPoint joinPoint) {
        className = joinPoint.getTarget().getClass().getSimpleName();
        methodName = joinPoint.getSignature().getName();
        methodArgs = Arrays.toString(joinPoint.getArgs())
                .replace('[', '(')
                .replace(']', ')');
    }
}
