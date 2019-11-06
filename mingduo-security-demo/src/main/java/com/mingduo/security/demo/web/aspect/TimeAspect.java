package com.mingduo.security.demo.web.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * 
 * @description:
 * @since 2019/10/17
 * @author : weizc 
 */
@Slf4j
@Aspect
@Component
public class TimeAspect {

    @Pointcut("execution(* com.mingduo.security.demo.web.controller.*.*(..))")
    public void pointcut(){}


    @Around("pointcut()")
    public Object handleMethod(ProceedingJoinPoint pjp) throws Throwable {

        System.out.print("time aspect  start");

        Object[] args = pjp.getArgs();
        for(Object arg:args){
            log.info("arg is "+arg);
        }
        StopWatch stopWatch=new StopWatch();
        stopWatch.start();
        Object object = pjp.proceed();

        stopWatch.stop();
        log.info(stopWatch.prettyPrint());

        log.info("time aspect end");

        return object;
    }

}
