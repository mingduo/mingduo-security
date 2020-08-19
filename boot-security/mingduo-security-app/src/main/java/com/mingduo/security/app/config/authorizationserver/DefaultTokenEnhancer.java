package com.mingduo.security.app.config.authorizationserver;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *  默认的要在  jwt token Enhancer 执行前执行
 * @description:
 * @since 2019/11/22
 * @author : weizc
 * @see DefaultTokenServices#createAccessToken(org.springframework.security.oauth2.provider.OAuth2Authentication, org.springframework.security.oauth2.common.OAuth2RefreshToken)
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DefaultTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;

        Map<String, Object> additionInfo = new HashMap<>(2);
        // info是要往JWT中放入的信息

        additionInfo.put("author","mingduo");

        defaultOAuth2AccessToken.setAdditionalInformation(Collections.unmodifiableMap(additionInfo));
        return defaultOAuth2AccessToken;
    }
}
