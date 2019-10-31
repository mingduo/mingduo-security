package com.mingduo.security.core.social.weixin.api;

/**
 * 
 * @description:
 * @since 2019/10/31
 * @author : weizc 
 */
public interface Weixin {

    WeixinUserInfo getUserInfo(String openId);
}
