package com.mingduo.security.core.authentication;

import com.mingduo.security.core.constants.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 *
 *   表单登录配置
 * @author : weizc
 * @description:
 * @since 2019/10/28
 */
@Configuration
public class FormAuthenticationConfig {

    @Autowired
    AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;

    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_LOGIN_PAGE)
                .loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler);
    }
}
