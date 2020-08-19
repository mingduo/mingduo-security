package com.mingduo.security.core.properties;

import lombok.Data;

/**
 * @author : weizc
 * @description:
 * @since 2019/10/31
 */
@Data
public class SocialAppInfo {

    private String appId;

    private String appSecret;

    private String providerId;
}
