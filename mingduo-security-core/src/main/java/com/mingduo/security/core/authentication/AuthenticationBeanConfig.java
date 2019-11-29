package com.mingduo.security.core.authentication;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * 认证相关的扩展点配置。配置在这里的bean，业务系统都可以通过声明同类型或同名的bean来覆盖安全
 * 模块默认的配置。
 * @description:
 * @since 2019/11/8
 * @author : weizc 
 */
@Configuration
public class AuthenticationBeanConfig {

    @ConditionalOnMissingBean(PasswordEncoder.class)
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @ConditionalOnMissingClass(value ="com.mingduo.security.authorize.authentication.RbacUserDetailService" )
    @Bean
    public UserDetailsService defaultUserDetailService(){
       return new DefaultUserDetailService();
    }

    @ConditionalOnMissingBean(SocialUserDetailsService.class)
    @Bean
    public SocialUserDetailsService defaultSocialUserDetailsService(){
        return new DefaultSocialUserDetailsService();
    }


}
