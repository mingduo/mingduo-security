package com.mingduo.security.browser.config;

import com.mingduo.security.core.authentication.FormAuthenticationConfig;
import com.mingduo.security.core.authentication.mobile.SmsAuthenticationSecurityConfig;
import com.mingduo.security.core.constants.SecurityConstants;
import com.mingduo.security.core.properties.SecurityProperites;
import com.mingduo.security.core.validate.code.ValidateCodeFilter;
import com.mingduo.security.core.validate.code.conf.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * 浏览器环境下安全配置主类
 *
 * @author : weizc
 * @description:
 * @since 2019/10/18
 */

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    SecurityProperites securityProperites;
    @Autowired
    ValidateCodeFilter validateCodeFilter;
    @Autowired
    DataSource dataSource;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    SmsAuthenticationSecurityConfig smsAuthenticationSecurityConfig;
    @Autowired
    ValidateCodeSecurityConfig validateCodeSecurityConfig;
    @Autowired
    FormAuthenticationConfig formAuthenticationConfig;

    @Autowired
    SpringSocialConfigurer socialConfigurer;
    @Autowired
    InvalidSessionStrategy invalidSessionStrategy;
    @Autowired
    SessionInformationExpiredStrategy sessionInformationExpiredStrategy;
    @Autowired
    LogoutSuccessHandler logoutSuccessHandler;



    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        //    tokenRepository.setCreateTableOnStartup(true);
        tokenRepository.setDataSource(dataSource);

        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);

        // 表单登录配置

        formAuthenticationConfig.configure(http);

        http
                //记住我配置，如果想在'记住我'登录时记录日志，可以注册一个InteractiveAuthenticationSuccessEvent事件的监听器
                .rememberMe()
                .userDetailsService(userDetailsService)
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperites.getBrowser().getRememberMeSeconds())
                .and()
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
                .and()
                //logout处理
                .logout()
                .logoutUrl("/signOut")
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .and()
                //session配置策略
                .sessionManagement()
              //  .invalidSessionUrl("/session/invalid")
               .invalidSessionStrategy(invalidSessionStrategy)
                .maximumSessions(securityProperites.getBrowser().getSession().getMaximumSessions())
                .maxSessionsPreventsLogin(securityProperites.getBrowser().getSession().isMaxSessionsPreventsLogin())
                .expiredSessionStrategy(sessionInformationExpiredStrategy)
        ;

        http
                .csrf()
                .disable();

        http.apply(socialConfigurer);
        http.apply(smsAuthenticationSecurityConfig);
        http.apply(validateCodeSecurityConfig);

    }
}
