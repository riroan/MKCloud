package com.openbox.backend.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@Aspect
public class TimeTraceAop {
    @Around("execution(* com.openbox.backend..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String uuid = UUID.randomUUID().toString();
        log.info("[{}]START: {}", uuid, joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            log.info("[{}]END: {} {}ms", uuid, joinPoint, timeMs);
        }
    }
}
