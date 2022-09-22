package com.github.kly.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/*
 * @author: Lingyu Kong
 * @description: 定义切面类，根据注解@KLoger位置，使用joinPoint动态获取被注解标识的方法名，并传入log日志中
 * 日志包括: 进入方法，离开方法，方法耗时，异常类型和信息
 */

@Configuration
@Aspect
@Slf4j
public class KLogerAspect {
    private static final Logger logger = LoggerFactory.getLogger(KLogerAspect.class);
    @Pointcut("@annotation(KLoger)")
    public void kLogerAspect(){}

    /*
     * @description:
       * @Param joinPoint: 用于控制被注解方法的运行和动态获取方法名，参数等
       * @return: 返回的result是被注解方法的返回值，如果不返回result会造成被注解方法无返回值
     */
    @Around("kLogerAspect()")
    public Object AroundLogPointCut(ProceedingJoinPoint joinPoint) throws Exception {
        long startTime = System.currentTimeMillis();
        Object result = recordLog(joinPoint,startTime);
        return result;
    }

    /*
     * @description:
       * @Param joinPoint: 使用result = joinPoint.proceed()表达式即可控制被注解的方法运行，并获取方法运行的返回值
       * @Param startTime: 用于计算运行时间
       * @return: 被注解方法的运行结果
     */
    private Object recordLog(ProceedingJoinPoint joinPoint, Long startTime) throws Exception {
        String methodName = joinPoint.getSignature().getName();
        Object result =null;
        try{
            logger.info("====================================================");
            logger.info("ENTER METHOD:{}",methodName);
            result = joinPoint.proceed();
        }catch (Throwable e){
            logger.error("ENCOUNTER EXCEPTION:{}, MESSAGE:{} ",e.getClass().getName(), e.getMessage());
            throw new Exception(e.getMessage());
        }
        long time =  System.currentTimeMillis() - startTime;
        logger.info("LEAVE METHOD:{}, COST TIME:{} ms",methodName,time);
        logger.info("====================================================");
        return result;
    }

}