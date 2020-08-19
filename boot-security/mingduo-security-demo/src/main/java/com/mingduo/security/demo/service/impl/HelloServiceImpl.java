package com.mingduo.security.demo.service.impl;

import com.mingduo.security.demo.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 
 * @description:
 * @since 2019/10/16
 * @author : weizc 
 */
@Slf4j
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String greeting(String name) {
        log.info("greeting");
        return "hello:"+name;
    }
}
