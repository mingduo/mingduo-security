package com.mingduo.security.core.social.weixin.connect;

import lombok.Getter;
import lombok.Setter;
import org.springframework.social.oauth2.AccessGrant;

/**
 *
 * 微信的access_token信息。与标准OAuth2协议不同，微信在获取access_token时会同时返回openId,并没有单独的
 * 通过accessToke换取openId的服务
 *
 * 所以在这里继承了标准AccessGrant，添加了openId字段，作为对微信access_token信息的封装。
 *
 * @since 2019/11/1
 * @author : weizc 
 */
@Setter
@Getter
public class WeixinAccessGrant extends AccessGrant {
    /**
     * {
     * "access_token":"ACCESS_TOKEN",
     * "expires_in":7200,
     * "refresh_token":"REFRESH_TOKEN",
     * "openid":"OPENID",
     * "scope":"SCOPE",
     * "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
     * }
     */
    private String openId;

    public WeixinAccessGrant(String accessToken,String openId) {
        super(accessToken);
        this.openId=openId;

    }

    public WeixinAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn,String openId) {
        super(accessToken, scope, refreshToken, expiresIn);
        this.openId=openId;

    }
}
