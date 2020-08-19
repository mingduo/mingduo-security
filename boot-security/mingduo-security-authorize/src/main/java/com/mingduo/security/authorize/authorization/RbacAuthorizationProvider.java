package com.mingduo.security.authorize.authorization;

import com.mingduo.security.core.authorization.AuthorizeConfigProvider;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author : weizc
 * @apiNode:
 * @since 2019/11/28
 */
@Order(Ordered.LOWEST_PRECEDENCE)
@Component
public class RbacAuthorizationProvider implements AuthorizeConfigProvider {

    /**
     * @param registry
     * @return
     * request authentication 值为什么这么写
     * @see SecurityExpressionRoot
     * @see org.springframework.security.web.access.expression.WebSecurityExpressionRoot
     * 基于 spel
     */
    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        registry.anyRequest().access("@rbacService.hasPermission(request,authentication)");
        return true;
    }
}
