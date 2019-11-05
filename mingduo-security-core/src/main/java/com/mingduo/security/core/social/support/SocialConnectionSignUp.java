package com.mingduo.security.core.social.support;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

/**
 * 
 * @description:
 * @since 2019/11/5
 * @author : weizc 
 */
public class SocialConnectionSignUp implements ConnectionSignUp {
    /**
     *  @see  org.springframework.social.security.SocialAuthenticationProvider#toUserId(org.springframework.social.connect.Connection)
     * @param connection
     * @return
     */
    @Override
    public String execute(Connection<?> connection) {
        //根据社交用户信息默认创建用户并返回用户唯一标识

        return connection.getKey().getProviderUserId();
    }
}
