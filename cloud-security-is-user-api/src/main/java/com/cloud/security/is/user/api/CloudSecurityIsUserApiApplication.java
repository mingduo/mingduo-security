package com.cloud.security.is.user.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CloudSecurityIsUserApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudSecurityIsUserApiApplication.class, args);
    }

}
