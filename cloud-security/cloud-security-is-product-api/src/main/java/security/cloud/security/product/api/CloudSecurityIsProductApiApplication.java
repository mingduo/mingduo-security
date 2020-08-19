package security.cloud.security.product.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


@SpringBootApplication
public class CloudSecurityIsProductApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudSecurityIsProductApiApplication.class, args);
    }

}
