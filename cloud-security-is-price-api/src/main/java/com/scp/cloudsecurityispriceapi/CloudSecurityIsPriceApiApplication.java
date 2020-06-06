package com.scp.cloudsecurityispriceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CloudSecurityIsPriceApiApplication {
    /**
     * -javaagent:D:\\idea\\learn\\mingduo-security\\cloud-security-monitor\\pinpoint-agent-2.0.2\\pinpoint-bootstrap-2.0.2.jar -Dpinpoint.agentId=9527
     * -Dpinpoint.applicationName=priceAPI
     * -Dpinpoint.profiler.profiles.active=priceapi
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(CloudSecurityIsPriceApiApplication.class, args);
    }

}
