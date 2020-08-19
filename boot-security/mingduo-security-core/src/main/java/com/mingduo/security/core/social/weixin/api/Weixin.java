package com.mingduo.security.core.social.weixin.api;

/**
 *
 * 微信API调用接口
 * @since 2019/10/31
 * @author : weizc 
 */
public interface Weixin {

    WeixinUserInfo getUserInfo(String openId);
}
