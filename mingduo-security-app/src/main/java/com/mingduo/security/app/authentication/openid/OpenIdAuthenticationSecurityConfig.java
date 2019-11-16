package com.mingduo.security.app.authentication.openid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.connect.UsersConnectionRepository;

/**
 * @author :    weizc
 * @since 2019/11/16
 */
@Configuration
public class OpenIdAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    AuthenticationSuccessHandler successHandler;
    @Autowired
    AuthenticationFailureHandler failureHandler;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    UsersConnectionRepository usersConnectionRepository;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        OpenIdAuthenticationFilter openIdAuthenticationFilter = new OpenIdAuthenticationFilter();
        openIdAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
        openIdAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);
        openIdAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        OpenIdAuthenticationProvider openIdAuthenticationProvider = new OpenIdAuthenticationProvider(userDetailsService, usersConnectionRepository);

        http.authenticationProvider(openIdAuthenticationProvider).addFilterAfter(openIdAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
