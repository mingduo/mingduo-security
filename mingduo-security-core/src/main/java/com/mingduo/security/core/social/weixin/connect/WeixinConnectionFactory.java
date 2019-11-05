package com.mingduo.security.core.social.weixin.connect;

import com.mingduo.security.core.social.weixin.api.Weixin;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 *
 * 微信连接工厂
 *
 * @since 2019/10/31
 * @author : weizc
 */
public class WeixinConnectionFactory extends OAuth2ConnectionFactory<Weixin> {

    /**
     *
     * @param providerId
     * @param appId
     * @param appSecret
     */
    public WeixinConnectionFactory(String providerId, String appId,String appSecret) {
        super(providerId, new WeixinServiceProvider(appId,appSecret) , new WeixinApiAdapter());
    }

    /**
     * 由于微信的openId是和accessToken一起返回的，所以在这里直接根据accessToken设
     * 置providerUserId即可，不用像QQ那样通过QQAdapter来获取
     */
    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        WeixinAccessGrant weixinAccessGrant = (WeixinAccessGrant) accessGrant;

        return weixinAccessGrant.getOpenId();
    }

    /**
     *
     * @param accessGrant
     * @return
     */
    @Override
    public Connection<Weixin> createConnection(AccessGrant accessGrant) {
        return new OAuth2Connection<>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(), new WeixinApiAdapter(extractProviderUserId(accessGrant)));
    }

    /**
     *
     * @param data
     * @return
     */
    @Override
    public Connection<Weixin> createConnection(ConnectionData data) {
        return new OAuth2Connection<>(data, getOAuth2ServiceProvider(), new WeixinApiAdapter(data.getProviderUserId()));
    }


    /**
     * 私有化方法 无法直接使用 自己写一个
     * @return
     */
    private OAuth2ServiceProvider<Weixin> getOAuth2ServiceProvider() {
        return (OAuth2ServiceProvider<Weixin>) getServiceProvider();
    }
}
