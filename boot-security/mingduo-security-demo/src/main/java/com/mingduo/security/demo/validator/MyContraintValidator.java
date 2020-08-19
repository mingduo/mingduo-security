package com.mingduo.security.demo.validator;

import com.mingduo.security.demo.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 
 * @description:
 * @since 2019/10/16
 * @author : weizc 
 */
@Slf4j
public class MyContraintValidator implements ConstraintValidator<MyContraint, Object> {
    @Autowired
    HelloService helloService;

    @Override
    public void initialize(MyContraint constraintAnnotation) {
        log.info("MyContraint init");
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        log.info("value = " + value );

        helloService.greeting("tom");
        return true;
    }
}
