package com.mingduo.security.core.properties;

import lombok.Data;

/**
 * QQ登录配置项
 *
 * @author : weizc
 * @description:
 * @since 2019/10/30
 */
@Data
public class QQProperties extends SocialAppInfo{


    /**
     * 第三方id，用来决定发起第三方登录的url，默认是 qq。
     */
    public QQProperties() {
        setProviderId("qq");
    }
}
