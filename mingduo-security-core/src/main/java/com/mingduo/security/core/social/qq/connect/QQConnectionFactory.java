package com.mingduo.security.core.social.qq.connect;

import com.mingduo.security.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 *
 * 泛型是API的类型
 *
 * @since 2019/10/30
 * @author : weizc 
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    /**
     * Create a {@link OAuth2ConnectionFactory}.
     *
     * providerId：服务提供商的唯一标志，通过配置文件配置
     * @param providerId
     * @param appId
     * @param appSecret
     *
     * */
    public QQConnectionFactory(String providerId,  String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId,appSecret), new QQApiAdapter());
    }
}
