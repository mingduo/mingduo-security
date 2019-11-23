package com.mingduo.security.ssoclient1;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableOAuth2Sso
@SpringBootApplication
public class SsoClientAApplication {


    /**
     * 获取当前登录的Authentication信息，SpringSecurity会自动注入
     * @param user
     * @return
     */
    @GetMapping("/user")
    public Authentication user(Authentication user){
        return user;
    }



    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(new JwtAccessTokenConverter());
    }

    public static void main(String[] args) {
        SpringApplication.run(SsoClientAApplication.class, args);
    }

}
