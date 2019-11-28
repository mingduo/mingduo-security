package com.mingduo.security.core.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author : weizc
 * @apiNode:
 * @since 2019/11/28
 */
@Component
public class DelegateAuthorizeConfigManager implements AuthorizeConfigManager {
    @Autowired
    List<AuthorizeConfigProvider> authorizeConfigProviders;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        authorizeConfigProviders.forEach(provider -> provider.config(registry));

        registry.anyRequest().authenticated();
    }
}
