package com.mingduo.security.browser.authorization;

import com.mingduo.security.core.authorization.AuthorizeConfigProvider;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 *
 * 浏览器环境默认的授权配置，对常见的静态资源，如js,css，图片等不验证身份
 *
 * @author : weizc
 * @apiNode:
 * @since 2019/11/28
 */
@Component
public class BrowserAuthorizeConfigProvider implements AuthorizeConfigProvider {


    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        registry.antMatchers(HttpMethod.GET,
                "/**/*.js",
                "/**/*.css",
                "/**/*.jpg",
                "/**/*.png",
                "/**/*.gif").permitAll();
        return false;
    }
}
