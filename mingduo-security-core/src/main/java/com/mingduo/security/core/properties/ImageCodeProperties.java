package com.mingduo.security.core.properties;

import lombok.Data;

/**
 * 
 * @description:
 * @since 2019/10/24
 * @author : weizc 
 */

@Data
public class ImageCodeProperties {

    /**
     * 图片宽
     */
    private int width = 67;
    /**
     * 图片高
     */
    private int height = 23;

    /**
     * 验证码长度
     */
    private int length=4;

    /**
     * 过期时间
     */
    private int expireIn = 60;

    /**
     * 要拦截的url，多个url用逗号隔开，ant pattern
     */
    private String url;
}
