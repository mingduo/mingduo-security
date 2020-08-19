package com.mingduo.security.zuulgateway.config;

import com.mingduo.security.zuulgateway.service.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

/**
 * 
 * @apiNode:
 * @since 2020/3/26
 * @author : weizc 
 */
@AllArgsConstructor
@Component
public class GatewayWebSecurityExpressionHandler extends OAuth2WebSecurityExpressionHandler {
    private final PermissionService permissionService;
    @Override
    protected StandardEvaluationContext createEvaluationContextInternal(Authentication authentication, FilterInvocation invocation) {
        StandardEvaluationContext evaluationContext = super.createEvaluationContextInternal(authentication, invocation);
        evaluationContext.setVariable("permissionService",permissionService);
        return evaluationContext;
    }
}
