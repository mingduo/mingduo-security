package com.mingduo.security.core.properties;

import lombok.Data;

/**
 *  图片验证码配置项
 * @description:
 * @since 2019/10/24
 * @author : weizc 
 */

@Data
public class ImageCodeProperties extends SmsCodeProperties{

    /**
     * 图片宽
     */
    private int width = 67;
    /**
     * 图片高
     */
    private int height = 23;

    public ImageCodeProperties() {
        setLength(4);
    }
}
