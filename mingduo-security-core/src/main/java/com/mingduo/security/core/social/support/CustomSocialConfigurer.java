package com.mingduo.security.core.social.support;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 
 * @description:
 * @since 2019/10/31
 * @author : weizc 
 */
public class CustomSocialConfigurer extends SpringSocialConfigurer {

    private final String filterProcessesUrl;

    private final AuthenticationSuccessHandler successHandler;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }

    public CustomSocialConfigurer(String filterProcessesUrl, AuthenticationSuccessHandler successHandler) {
        this.filterProcessesUrl=filterProcessesUrl;
        this.successHandler = successHandler;
    }

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter socialAuthenticationFilter = ((SocialAuthenticationFilter) super.postProcess(object));
        // SocialAuthenticationFilter过滤器默认拦截的请求是/auth开头，这里是修改为自己配置的
        socialAuthenticationFilter.setFilterProcessesUrl(filterProcessesUrl);
        //todo 配置登录成功 失败处理器 支持  json
        socialAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);

        return (T) socialAuthenticationFilter;
    }
}
