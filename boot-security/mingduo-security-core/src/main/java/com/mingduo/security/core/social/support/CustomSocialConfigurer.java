package com.mingduo.security.core.social.support;

import lombok.Data;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 *  实现social 配置
 * @description:
 * @since 2019/10/31
 * @author : weizc 
 */
@Data
public class CustomSocialConfigurer extends SpringSocialConfigurer {

    private String filterProcessesUrl;

    private  AuthenticationSuccessHandler successHandler;
    
    @Autowired(required = false)
    ObjectProvider<SocialAuthenticationFilterPostProcessor> authenticationFilterPostProcessorObjectProvider;
    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }

    public CustomSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl=filterProcessesUrl;
    }

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter socialAuthenticationFilter = ((SocialAuthenticationFilter) super.postProcess(object));
        // SocialAuthenticationFilter过滤器默认拦截的请求是/auth开头，这里是修改为自己配置的
        socialAuthenticationFilter.setFilterProcessesUrl(filterProcessesUrl);

        authenticationFilterPostProcessorObjectProvider.ifAvailable(t->t.process(socialAuthenticationFilter));
        //socialAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);

        return (T) socialAuthenticationFilter;
    }
}
