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
    /**
     * 可以调整优先级
     */
    @Autowired
    List<AuthorizeConfigProvider> authorizeConfigProviders;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        boolean exsitAnyRequest =false;
        for (AuthorizeConfigProvider provider : authorizeConfigProviders) {
            boolean currentIsAnyRequestConfig =provider.config(registry);



            // 如果当前配置有anyRequest配置，那么把existAnyRequestConfig置为true，标识已经有了anyRequest配置

            if(currentIsAnyRequestConfig){
                exsitAnyRequest=true;
            }

        }
        // 如果系统中没有配置anyRequest，那么增加一个anyRequest

        if(!exsitAnyRequest){
            registry.anyRequest().authenticated();

        }
    }
}
