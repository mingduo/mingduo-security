package com.mingduo.security.core.properties;

import lombok.Data;

/**
 *
 * 微信登录配置项
 * @since 2019/10/31
 * @author : weizc 
 */
@Data
public class WeixinProperties extends SocialAppInfo{


    /**
     * 第三方id，用来决定发起第三方登录的url，默认是 weixin。
     */
    public WeixinProperties() {
        setProviderId("weixin");
    }

}
