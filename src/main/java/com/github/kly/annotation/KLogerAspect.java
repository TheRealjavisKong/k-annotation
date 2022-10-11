package com.github.kly.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lingyu Kong
 * @version 1.0.4
 */
@Configuration
@Aspect
@Slf4j
public class KLogerAspect {

    /**
     * @author Lingyu Kong
     */
    @Pointcut("@annotation(KLoger)")
    public void kLogerAspect(){}

    /**
     * @author Lingyu Kong
     * @param joinPoint  被注解的方法
     * @return  java.lang.Object
     */
    @Around("kLogerAspect()")
    public Object aroundLogPointCut(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = recordLog(joinPoint,startTime);
        return result;
    }

    /**
     * @author Lingyu Kong
     * @param joinPoint 被注解的方法
     * @param startTime 被注解方法开始运行的时间
     * @return java.lang.Object
     */
    private Object recordLog(ProceedingJoinPoint joinPoint, Long startTime) throws Throwable {
        String classname = joinPoint.getTarget().getClass().getSimpleName();
        final Logger logger = LoggerFactory.getLogger(classname);
        String methodName = joinPoint.getSignature().getName();
        Object result =null;
        logger.info("====================================================");
        logger.info("ENTER METHOD:{}",methodName);
        result = joinPoint.proceed();
        long time =  System.currentTimeMillis() - startTime;
        logger.info("LEAVE METHOD:{}, COST TIME:{} ms",methodName,time);
        logger.info("====================================================");
        return result;
    }

}