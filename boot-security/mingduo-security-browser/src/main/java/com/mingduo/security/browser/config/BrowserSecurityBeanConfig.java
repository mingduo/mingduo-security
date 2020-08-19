package com.mingduo.security.browser.config;

import com.mingduo.security.browser.logout.BrowserLogoutSuccessHandler;
import com.mingduo.security.browser.session.BrowserInvalidSessionStrategy;
import com.mingduo.security.browser.session.BrowserSessionInformationExpiredStrategy;
import com.mingduo.security.core.properties.SecurityProperites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * 
 * @description:
 * @since 2019/11/7
 * @author : weizc 
 */
@Configuration
public class BrowserSecurityBeanConfig {


    @Autowired
    SecurityProperites securityProperites;
    /**
     * session失效时的处理策略配置
     * @return
     */
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    @Bean
    public InvalidSessionStrategy invalidSessionStrategy(){
        return new BrowserInvalidSessionStrategy(securityProperites);

    }
    /**
     * 并发登录导致前一个session失效时的处理策略配置
     * @return
     */
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    @Bean
    public SessionInformationExpiredStrategy SessionInformationExpiredStrategy(){
        return new BrowserSessionInformationExpiredStrategy(securityProperites);
    }
    /**
     * 退出时的处理策略配置
     *
     * @return
     */
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new BrowserLogoutSuccessHandler(securityProperites);
    }
}
