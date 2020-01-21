package cloud.security.order.api.config;

import cloud.security.order.api.domain.MyUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.*;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/1/16
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 现在微服务由网关转发 所有请求都不需要拦截
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll()
                .and()
                .csrf().disable();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
        authenticationManager.setTokenServices(resourceServerTokenServices());

        return authenticationManager;

    }


    @Bean
    public ResourceServerTokenServices resourceServerTokenServices() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setClientId("order");
        tokenServices.setClientSecret("123456");
        tokenServices.setCheckTokenEndpointUrl("http://localhost:9090/oauth/check_token");
        tokenServices.setAccessTokenConverter(getAccessTokenConverter());
        return tokenServices;
    }

    /**
     * 自定义实现
     *
     * @return
     */
    private AccessTokenConverter getAccessTokenConverter() {
        DefaultAccessTokenConverter converter = new DefaultAccessTokenConverter();
        DefaultUserAuthenticationConverter userAuthenticationConverter = new DefaultUserAuthenticationConverter();
        userAuthenticationConverter.setUserDetailsService(new MyUserDetailService());
        converter.setUserTokenConverter(userAuthenticationConverter);
        return converter;
    }
}
