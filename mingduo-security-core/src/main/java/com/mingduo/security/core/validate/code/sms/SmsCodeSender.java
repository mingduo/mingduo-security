package com.mingduo.security.core.validate.code.sms;

/**
 *
 * 短信验证码生成器
 * @since 2019/10/25
 * @author : weizc 
 */
public interface SmsCodeSender {
    /**
     * @param mobile
     * @param code
     */
    void send(String mobile,String code);
}
