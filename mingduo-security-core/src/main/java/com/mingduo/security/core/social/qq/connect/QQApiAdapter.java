package com.mingduo.security.core.social.qq.connect;

import com.mingduo.security.core.social.qq.api.QQ;
import com.mingduo.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 
 * @description:
 * @since 2019/10/30
 * @author : weizc 
 */
public class QQApiAdapter implements ApiAdapter<QQ> {
    @Override
    public boolean test(QQ api) {
        return true;
    }

    // 将个性化的数据转换为标准的数据结构
    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        // 获取用户信息
        QQUserInfo userInfo = api.getUserInfo();
        // 显示的用户名字
        values.setDisplayName(userInfo.getNickname());
        // 用户头像
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        // 个人主页，qq没有
        values.setProfileUrl(null);
        // 服务提供商的用户id，即openId
        values.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    @Override
    public void updateStatus(QQ api, String message) {

    }
}
