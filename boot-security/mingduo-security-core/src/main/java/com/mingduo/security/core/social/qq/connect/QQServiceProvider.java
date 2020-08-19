package com.mingduo.security.core.social.qq.connect;

import com.mingduo.security.core.social.qq.api.QQ;
import com.mingduo.security.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * 
 * @description:
 * @since 2019/10/30
 * @author : weizc 
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {


    private String appId;


    private static final String URL_AUTHORIZE="https://graph.qq.com/oauth2.0/authorize";

    private static final String URL_ACCESS_TOKEN="https://graph.qq.com/oauth2.0/token";
    /**
     *
     * https://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token
     *
     * Create a new {@link OAuth2ServiceProvider}.
     *
     */
    public QQServiceProvider(String appId,String appSecret) {
        super(new QQOauth2Template(appId, appSecret,
                URL_AUTHORIZE, URL_ACCESS_TOKEN));

        this.appId=appId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken,appId);
    }
}
