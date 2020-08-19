package com.mingduo.security.core.authorization;

import com.mingduo.security.core.constants.SecurityConstants;
import com.mingduo.security.core.properties.SecurityProperites;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 *
 * 核心模块的授权配置提供器，安全模块涉及的url的授权配置在这里。
 * @author : weizc
 * @apiNode:
 * @since 2019/11/28
 */
@Component
public class CoreAuthorizeConfigProvider implements AuthorizeConfigProvider {
    @Autowired
    SecurityProperites securityProperites;

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        registry.antMatchers(SecurityConstants.DEFAULT_LOGIN_PAGE,
                SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL,
                securityProperites.getBrowser().getSignInPage(),
                securityProperites.getBrowser().getSignUpUrl(),
                "/user/register",
                securityProperites.getBrowser().getSession().getSessionInvalidUrl())
                .permitAll();

        if(StringUtils.isNotBlank(securityProperites.getBrowser().getSignOutPage())){
            registry.antMatchers(securityProperites.getBrowser().getSignOutPage()).permitAll();
        }
        return false;

    }
}
