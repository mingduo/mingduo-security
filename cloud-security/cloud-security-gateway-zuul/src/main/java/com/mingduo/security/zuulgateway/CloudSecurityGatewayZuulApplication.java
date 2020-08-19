package com.mingduo.security.zuulgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


@EnableZuulProxy
@SpringBootApplication
public class CloudSecurityGatewayZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudSecurityGatewayZuulApplication.class, args);
    }

}
