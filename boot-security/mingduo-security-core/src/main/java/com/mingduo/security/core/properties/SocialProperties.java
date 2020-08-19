package com.mingduo.security.core.properties;

import lombok.Data;

/**
 * 
 * @description:
 * @since 2019/10/30
 * @author : weizc 
 */
@Data
public class SocialProperties {

    private QQProperties qq=new QQProperties();


    private WeixinProperties weixin=new WeixinProperties();


    /**
     * 社交登录功能拦截的url
     */
    private String filterProcessesUrl = "/auth";
}
