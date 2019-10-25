package com.mingduo.security.core.properties;

import lombok.Data;

/**
 * 验证码配置
 *
 * @author : weizc
 * @description:
 * @since 2019/10/24
 */
@Data
public class ValidateCodeProperties {

    /**
     * 图片验证码配置
     */
    private ImageCodeProperties image = new ImageCodeProperties();

    /**
     * 短信验证码配置
     */
    private SmsCodeProperties sms=new SmsCodeProperties();

}
