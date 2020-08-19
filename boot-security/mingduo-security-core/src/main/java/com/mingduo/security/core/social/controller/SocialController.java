package com.mingduo.security.core.social.controller;

import com.mingduo.security.core.social.support.SocialUserInfo;
import org.springframework.social.connect.Connection;

/**
 * 
 * @description:
 * @since 2019/11/20
 * @author : weizc 
 */
public abstract class SocialController {

    protected SocialUserInfo buildSocialUserInfo(Connection connection) {
        return new SocialUserInfo()
                .setProviderId(connection.getKey().getProviderId())
                .setProviderUserId(connection.getKey().getProviderUserId())
                .setNickName(connection.getDisplayName())
                .setHeading(connection.getImageUrl());
    }
}
