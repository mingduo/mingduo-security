package com.mingduo.security.core.validate.code.sms;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * 默认的短信验证码发送器
 * @since 2019/10/25
 * @author : weizc 
 */
@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender{
    

    @Override
    public void send(String mobile, String code) {
        log.warn("请配置真实的短信验证码发送器(SmsCodeSender)");
        log.info("向手机[{}]发送验证码:{}",mobile,code);
    }
}
