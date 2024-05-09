package com.bachelor.thesis.organization_education.aop;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import com.bachelor.thesis.organization_education.annotations.Trimmed;
import com.bachelor.thesis.organization_education.requests.general.ListRequest;

import java.util.Arrays;
import java.util.Collection;
import java.lang.reflect.Field;

@Aspect
@Component
public class TrimmedStringAspect {
    @Around("execution(* com.bachelor.thesis.organization_education.controllers.university.*.*(..))")
    public Object trimStringValues(ProceedingJoinPoint joinPoint) throws Throwable {
        var args = joinPoint.getArgs();
        for (var arg : args) {
            if(arg instanceof ListRequest<?> collection) {
                trimCollection(collection.collection());
            } else {
                trimObject(arg);
            }
        }
        return joinPoint.proceed(args);
    }

    private void trimCollection(Collection<?> collection) {
        for (var object : collection) {
            trimObject(object);
        }
    }

    private void trimObject(Object obj) {
        if (obj == null) {
            return;
        }

        var clazz = obj.getClass();
        while (clazz != Object.class) {
            Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(Trimmed.class))
                    .forEach(field -> trimField(field, obj));

            clazz = clazz.getSuperclass();
        }
    }

    private void trimField(Field field, Object obj) {
        try {
            field.setAccessible(true);
            var fieldValue = field.get(obj);
            if (fieldValue instanceof String stringValue) {
                stringValue = stringValue.trim();
                field.set(obj, stringValue);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
