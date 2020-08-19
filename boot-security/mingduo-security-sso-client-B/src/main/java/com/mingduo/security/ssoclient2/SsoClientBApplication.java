package com.mingduo.security.ssoclient2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * client B
 */
@RestController
@EnableOAuth2Sso // 开启SSO
@SpringBootApplication
public class SsoClientBApplication {

    /**
     * 获取当前登录的Authentication信息，SpringSecurity会自动注入
     * @param user
     * @return
     */
    @GetMapping("/user")
    public Authentication user(Authentication user){
        return user;
    }

    public static void main(String[] args) {
        SpringApplication.run(SsoClientBApplication.class, args);
    }

}
