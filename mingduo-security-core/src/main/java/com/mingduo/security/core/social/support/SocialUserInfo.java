package com.mingduo.security.core.social.support;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @description:
 * @since 2019/11/4
 * @author : weizc 
 */
@Accessors(chain = true)
@Data
public class SocialUserInfo {
    private String providerId;
    private String providerUserId;

    private String nickName;
    /**
     * 头像
     */
    private String heading;
}
