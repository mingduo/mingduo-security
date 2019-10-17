package com.mingduo.security.demo.service.impl;

import com.mingduo.security.demo.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * 
 * @description:
 * @since 2019/10/16
 * @author : weizc 
 */
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String greeting(String name) {
        System.out.println("greeting");
        return "hello:"+name;
    }
}
