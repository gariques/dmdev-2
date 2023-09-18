package com.iddev.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ServiceLoggingAspect {

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void isServiceLayer() {
    }

    @Around("isServiceLayer() && target(service) && args(params,..)")
    public Object addLoggingAround(ProceedingJoinPoint joinPoint, Object service, Object params) throws Throwable {
        log.info("invoked service method - {} in class {} with params {}", joinPoint.getSignature().getName(), service, params);
        var result = joinPoint.proceed();
        log.info("invoked service method - {} in class {} result {}", joinPoint.getSignature().getName(), service, result);

        return result;
    }
}
