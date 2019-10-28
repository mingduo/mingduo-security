package com.mingduo.security.core.properties;

import lombok.Data;

/**
 *  短信 验证码
 * @description:
 * @since 2019/10/25
 * @author : weizc 
 */
@Data
public class SmsCodeProperties {

    /**
     * 验证码长度
     */
    private int length=6;

    /**
     * 过期时间
     */
    private int expireIn = 60*60;

    /**
     * 要拦截的url，多个url用逗号隔开，ant pattern
     */
    private String url;
}
