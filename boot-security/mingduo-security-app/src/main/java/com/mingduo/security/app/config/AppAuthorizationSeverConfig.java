package com.mingduo.security.app.config;

import com.mingduo.security.core.properties.OAuth2ClientProperties;
import com.mingduo.security.core.properties.SecurityProperites;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.OAuth2AuthorizationServerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证服务器 配置
 * <p>
 * copy from
 *
 * @author : weizc
 * @see OAuth2AuthorizationServerConfiguration
 * @since 2019/11/8
 */
@Configuration
@EnableAuthorizationServer
public class AppAuthorizationSeverConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    SecurityProperites securityProperites;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationConfiguration authenticationConfiguration;
    @Autowired
    TokenStore tokenStore;

    @Autowired
    List<TokenEnhancer> tokenEnhancers;
    @Autowired
    ObjectProvider<AccessTokenConverter> accessTokenConverter;


    /**
     * 认证及token配置
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints.userDetailsService(userDetailsService)
                .authenticationManager(authenticationConfiguration.getAuthenticationManager())
                .tokenStore(tokenStore);

        //
        accessTokenConverter.ifAvailable(endpoints::accessTokenConverter);

        // TokenEnhancerChain是增强器链
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancerChainList = new ArrayList<>(4);
        enhancerChainList.addAll(tokenEnhancers);

        enhancerChain.setTokenEnhancers(enhancerChainList);
        endpoints.tokenEnhancer(enhancerChain);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    }


    /**
     * 客户端配置
     * 我们这里不会像qq或者微信那样允许其他应用注册，所以将客户端信息放在内存中就可以了
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder inMemoryBuilder = clients.inMemory();
        List<OAuth2ClientProperties> clientPropertiesList = securityProperites.getOauth2().getClient();
        clientPropertiesList.forEach(
                clp -> inMemoryBuilder.withClient(clp.getClientId())
                        /**
                         * 需要根据加密 后的密码设置 密钥
                         * @see org.springframework.security.authentication.dao.DaoAuthenticationProvider.additionalAuthenticationChecks
                         */
                        .secret(passwordEncoder.encode(clp.getClientSecret()))
                        .scopes("all")
                        // 客户端支持的授权模式
                        .authorizedGrantTypes("refresh_token", "authorization_code", "password")
                        // 令牌的有效时间
                        .accessTokenValiditySeconds(clp.getAccessTokenValidateSeconds())
                        // 令牌的权限有哪些，可以配置多个，这里配置了，
                        // 客户端请求时就不用带scope参数了.如果客户端带了scope参数，
                        // 则必须是配置的scope中的其中一个，否则会报错
                        .refreshTokenValiditySeconds(2592000)

        );

    }


}
