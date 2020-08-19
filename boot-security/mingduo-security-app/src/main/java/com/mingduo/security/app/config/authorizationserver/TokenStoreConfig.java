package com.mingduo.security.app.config.authorizationserver;

import com.mingduo.security.core.properties.SecurityProperites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author : weizc
 * @description:
 * @since 2019/11/21
 */

@Configuration
public class TokenStoreConfig {

    @Autowired
    SecurityProperites securityProperites;
    /**
     * 使用redis存储token的配置，只有在my.security.oauth2.tokenStore配置为redis时生效
     */
    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @ConditionalOnProperty(prefix = "my.security.oauth2", name = "store-type", havingValue = "redis")
    @Bean
    public TokenStore reidisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }


    /**
     * 使用jwt时的配置，默认生效（matchIfMissing = true）
     */
    /**
     * @return 配置token的存储
     */
    @ConditionalOnProperty(prefix = "my.security.oauth2", name = "store-type", havingValue = "jwt", matchIfMissing = true)
    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }


    /**
     * @return 配置token的生成
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // 指定签名的秘钥
        converter.setSigningKey(securityProperites.getOauth2().getSigningKey());
        return converter;
    }


    /**
     * 自定义 tokenEnhancer
     * @return
     */
    @Bean
    public TokenEnhancer CustomtokenEnhancer (){
       return new DefaultTokenEnhancer();
    }
}
