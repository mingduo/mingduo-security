package com.mingduo.security.core.social.weixin.connect;

import lombok.Getter;
import lombok.Setter;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * 
 * @description:
 * @since 2019/10/31
 * @author : weizc 
 */
@Getter
@Setter
public class WeixinOauth2Template extends OAuth2Template {

    private  String clientId;

    private  String clientSecret;

    private  String accessTokenUrl;

    private  String authorizeUrl;




    public WeixinOauth2Template(String clientId, String clientSecret, String authenticateUrl, String accessTokenUrl) {
        super(clientId,clientSecret,authenticateUrl,accessTokenUrl);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authorizeUrl=authenticateUrl;
        this.accessTokenUrl = accessTokenUrl;
    }

    @Override
    public String buildAuthorizeUrl(GrantType grantType, OAuth2Parameters parameters) {
        String url = super.buildAuthorizeUrl(parameters);
        return String.format("%s&appId=%s&scope=snsapi_login", url, clientId);

    }
}
