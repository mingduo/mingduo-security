package com.cloud.security.is.user.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CloudSecurityIsUserApiApplication {
    /**
     * pinpoint 接入
     * -javaagent:D:\\idea\\learn\\mingduo-security\\cloud-security-monitor\\pinpoint-agent-2.0.2\\pinpoint-bootstrap-2.0.2.jar
     * -Dpinpoint.agentId=9527
     * -Dpinpoint.applicationName=userAPI
     * -Dpinpoint.profiler.profiles.active=userapi
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(CloudSecurityIsUserApiApplication.class, args);

    }

}
