package com.mingduo.security.core.social.weixin.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 *
 * Weixin API调用模板， scope为Request的Spring bean, 根据当前用户的accessToken创建。
 * @author : weizc
 * @description:
 * @since 2019/10/31
 */
@Slf4j
public class WeixinImpl extends AbstractOAuth2ApiBinding implements Weixin {
    ObjectMapper objectMapper = new ObjectMapper();
    /**
     * https://developers.weixin.qq.com/doc/oplatform/Website_App/WeChat_Login/Authorized_Interface_Calling_UnionID.html
     * http请求方式: GET
     * 获取用户个人信息（UnionID机制
     */
    private static final String URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?openid=%s";


    public WeixinImpl(String accessToken) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }



    @Override
    public WeixinUserInfo getUserInfo(String openId) {
        String url = String.format(URL_GET_USER_INFO, openId);
        String result = this.getRestTemplate().getForObject(url, String.class);

        log.info("获取微信登录信息 result:" + result);
        WeixinUserInfo userInfo = null;
        try {
            userInfo = objectMapper.readValue(result, WeixinUserInfo.class);
        } catch (IOException e) {
            throw new RuntimeException("获取用户信息失败", e);
        }

        return userInfo;
    }
}
