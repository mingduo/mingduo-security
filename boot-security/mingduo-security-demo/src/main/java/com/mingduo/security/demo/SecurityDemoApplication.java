package com.mingduo.security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author weizc
 */
@EnableSwagger2
@RestController
@SpringBootApplication(scanBasePackages = "com.mingduo.security")
public class SecurityDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityDemoApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello spring security";
    }
}
