package com.mingduo.security.core.social.weixin.connect;

import com.mingduo.security.core.social.weixin.api.Weixin;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 *
 * 微信连接工厂
 *
 * @since 2019/10/31
 * @author : weizc 
 */
public class WeixinConnectionFactory extends OAuth2ConnectionFactory<Weixin> {


    public WeixinConnectionFactory(String providerId, String appId,String appSecret) {
        super(providerId, new WeixinServiceProvider(appId,appSecret) , new WeixinApiAdapter());
    }
}
