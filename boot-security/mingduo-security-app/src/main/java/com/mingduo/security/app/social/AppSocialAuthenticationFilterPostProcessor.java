package com.mingduo.security.app.social;

import com.mingduo.security.core.social.support.SocialAuthenticationFilterPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @since  2019/11/16
 * @author :    weizc
 */
@Component
public class AppSocialAuthenticationFilterPostProcessor  implements SocialAuthenticationFilterPostProcessor {

    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;
    @Override
    public void process(SocialAuthenticationFilter socialAuthenticationFilter) {
        socialAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
    }
}
