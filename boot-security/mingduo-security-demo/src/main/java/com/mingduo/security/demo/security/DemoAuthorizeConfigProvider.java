package com.mingduo.security.demo.security;

import com.mingduo.security.core.authorization.AuthorizeConfigProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 
 * @apiNode:
 * @since 2019/11/28
 * @author : weizc 
 */
@Component
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {




    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        registry.antMatchers( "/user/register")
                .permitAll();
        return false;
    }


}
