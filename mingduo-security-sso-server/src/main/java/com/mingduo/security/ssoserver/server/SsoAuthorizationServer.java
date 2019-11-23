package com.mingduo.security.ssoserver.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author : weizc
 * @description:
 * @since 2019/11/23
 */
@EnableAuthorizationServer// 有了这个注解，当前应用就是一个标准的OAuth2认证服务器
@Configuration
public class SsoAuthorizationServer implements AuthorizationServerConfigurer {

    @Autowired
    PasswordEncoder passwordEncoder;
    /**
     * tokenKey-密钥  权限表达式
     * 配置使用JWT生产令牌
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("isAuthenticated()");


    }

    // 配置认证服务器需要给哪些应用发令牌，
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        String secret = passwordEncoder.encode("123456");

        clients.inMemory().withClient("client1").secret(secret)
                .authorizedGrantTypes("authorization_code", "refresh_token") // 支持的授权模式
                .scopes("all")
                .and().withClient("client2").secret(secret)
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .scopes("all");
    }

    // 认证服务器的安全配置，配置的 isAuthenticated() 是SpringSecurity的授权表达式，默认是 denyAll()，拒绝所有访问
    // 它的意思是访问访问认证服务器的tokenKey的时候，需要进行认证
    // tokenKey 就是对 JWT进行签名的key，
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(jwtTokenStore());

    }



    @Bean
    public JwtTokenStore jwtTokenStore(){

        return new JwtTokenStore(jwtAccessTokenConverter());
    }


    @Bean
    public  JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // 对JWT进行前面的key
        converter.setSigningKey("mingduo");
        return converter;
    }
}
