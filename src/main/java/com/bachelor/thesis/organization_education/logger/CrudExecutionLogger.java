package com.bachelor.thesis.organization_education.logger;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.Arrays;

@Aspect
@Slf4j
public class CrudExecutionLogger {
    private String className;
    private String methodName;
    private String methodArgs;

    @Before("execution(* com.bachelor.thesis.organization_education.services.implementations.CrudServiceAbstract(..))")
    public void beforeMethodExecution(JoinPoint joinPoint) {
        initData(joinPoint);
        log.info("Input to the {}.{}{} method", className, methodName, methodArgs);
    }

    @After("")
    public void afterMethodExecution(JoinPoint joinPoint) {
        initData(joinPoint);
        log.info("Exiting the {}.{}{} method", className, methodName, methodArgs);
    }

    @AfterThrowing(pointcut = "execution(* com.bachelor.thesis.organization_education.services.implementations.CrudServiceAbstract(..))", throwing = "e")
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
