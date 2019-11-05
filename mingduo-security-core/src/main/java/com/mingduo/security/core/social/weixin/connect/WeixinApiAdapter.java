package com.mingduo.security.core.social.weixin.connect;

import com.mingduo.security.core.social.weixin.api.Weixin;
import com.mingduo.security.core.social.weixin.api.WeixinUserInfo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 微信 api适配器，将微信 api的数据模型转为spring social的标准模型。
 *
 * @author : weizc
 * @since 2019/10/31
 */
@NoArgsConstructor
@AllArgsConstructor
public class WeixinApiAdapter implements ApiAdapter<Weixin> {
    String openId;


    @Override
    public boolean test(Weixin api) {
        return true;
    }

    /**
     *
     * @param api
     * @param values
     */
    @Override
    public void setConnectionValues(Weixin api, ConnectionValues values) {
        WeixinUserInfo userInfo = api.getUserInfo(openId);
        values.setProviderUserId(userInfo.getOpenid());
        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getHeadimgurl());
    }

    /**
     *
     * @param api
     * @return
     */
    @Override
    public UserProfile fetchUserProfile(Weixin api) {
        return null;
    }

    /**
     *
     * @param api
     * @param message
     */
    @Override
    public void updateStatus(Weixin api, String message) {

    }
}
