package com.mingduo.security.app.config;

import com.mingduo.security.app.authentication.openid.OpenIdAuthenticationSecurityConfig;
import com.mingduo.security.core.authentication.FormAuthenticationConfig;
import com.mingduo.security.core.authentication.mobile.SmsAuthenticationSecurityConfig;
import com.mingduo.security.core.constants.SecurityConstants;
import com.mingduo.security.core.properties.SecurityProperites;
import com.mingduo.security.core.validate.code.conf.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author : weizc
 * @description:
 * @since 2019/11/11
 */
@Configuration
@EnableResourceServer
public class AppResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    SecurityProperites securityProperites;
    @Autowired
    SmsAuthenticationSecurityConfig smsAuthenticationSecurityConfig;
    @Autowired
    ValidateCodeSecurityConfig validateCodeSecurityConfig;
    @Autowired
    FormAuthenticationConfig formAuthenticationConfig;
    @Autowired
    OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;

    @Autowired
    SpringSocialConfigurer socialConfigurer;


    @Override
    public void configure(HttpSecurity http) throws Exception {

        // 表单登录配置

        formAuthenticationConfig.configure(http);

        http
                .authorizeRequests()
                .antMatchers(SecurityConstants.DEFAULT_LOGIN_PAGE
                        , securityProperites.getBrowser().getSignInPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                        securityProperites.getBrowser().getSignUpUrl(),
                        // securityProperites.getBrowser().getSignOutPage(),
                        "/user/register",
                        securityProperites.getBrowser().getSession().getSessionInvalidUrl())
                .permitAll()
                .anyRequest()
                .authenticated()

        ;

        http
                .csrf()
                .disable();
        //社交登录
        http.apply(socialConfigurer);
        //短信登录
        http.apply(smsAuthenticationSecurityConfig);
        // 图片/短信验证码
        http.apply(validateCodeSecurityConfig);
        //app openId登录
        http.apply(openIdAuthenticationSecurityConfig);
    }
}
