package com.github.kly.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;


@Configuration
@Aspect
@Slf4j
public class KLogerAspect {
    private static final Logger logger = LoggerFactory.getLogger(KLogerAspect.class);
    @Pointcut("@annotation(KLoger)")
    public void kLogerAspect(){}


    @Around("kLogerAspect()")
    public Object AroundLogPointCut(ProceedingJoinPoint joinPoint) {
        long startTime = System.currentTimeMillis();
        long time =  System.currentTimeMillis() - startTime;
        Object result = recordLog(joinPoint,time);
        return result;
    }

    private Object recordLog(ProceedingJoinPoint joinPoint, Long time) {
        String methodName = joinPoint.getSignature().getName();
        Object result =null;
        try{
            logger.info("====================================================");
            logger.info("ENTER METHOD:{}",methodName);
            result = joinPoint.proceed();
        }catch (Throwable e){
            logger.error(e.getMessage());
        }
        logger.info("LEAVE METHOD:{}, COST TIME:{} ms",methodName,time);
        logger.info("====================================================");
        return result;
    }

}