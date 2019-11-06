package com.mingduo.security.demo.validator;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.validation.BindingResult;

/**
 * 
 * @description:
 * @since 2019/10/18
 * @author : weizc 
 */
//@Component
//@Aspect
    @Slf4j
public class VaildateAspect {

    @Pointcut(value = "execution(* com.mingduo.security.demo.web.controller.*.*(..))")
    public void vaildatePointcut(){}


    @Around(value = "vaildatePointcut")
    public Object handleVaildateResult(ProceedingJoinPoint pjp) throws Throwable {
        log.info("进入切片");

        for(Object arg:pjp.getArgs()){
            if(arg instanceof BindingResult){
                BindingResult errors = (BindingResult) arg;
                if(errors.hasErrors()){
                    throw new VaildateException(errors.getAllErrors());
                }
            }
        }
        return pjp.proceed();
    }
}
