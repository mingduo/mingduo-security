package com.mingduo.security.zuulgateway.config;

import com.mingduo.security.zuulgateway.filter.AuditLogOncePerFilter;
import com.mingduo.security.zuulgateway.filter.GatewayRateLimitFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

/**
 * 
 * @apiNode:
 * @since 2020/2/28
 * @author : weizc 
 */
@Configuration
@EnableResourceServer
public class GatewaySecurityConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    GatewayWebSecurityExpressionHandler gatewayWebSecurityExpressionHandler;
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("order-server")
        .expressionHandler(gatewayWebSecurityExpressionHandler);
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new GatewayRateLimitFilter(), SecurityContextPersistenceFilter.class)
                .addFilterBefore(new AuditLogOncePerFilter(), ExceptionTranslationFilter.class)
                .authorizeRequests()
                .antMatchers("/token/**").permitAll()
               .anyRequest().access("#permissionService.hasPermission(authentication,request)");
        //增加 .anyRequest().authenticated() 会覆盖 #permissionService.hasPermission(authentication,request)
        // .anyRequest().authenticated();


    }
}
