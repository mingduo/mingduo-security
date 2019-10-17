package com.mingduo.security.demo.validator;

import com.mingduo.security.demo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 
 * @description:
 * @since 2019/10/16
 * @author : weizc 
 */
public class MyContraintValidator implements ConstraintValidator<MyContraint, Object> {
    @Autowired
    HelloService helloService;

    @Override
    public void initialize(MyContraint constraintAnnotation) {
        System.out.println("MyContraint init");
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        System.out.println("value = " + value );

        helloService.greeting("tom");
        return true;
    }
}
