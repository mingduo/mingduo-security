package com.mingduo.security.core.validate.code.conf;

import com.mingduo.security.core.validate.code.ValidateCodeGenerator;
import com.mingduo.security.core.validate.code.image.ImageCodeGenerator;
import com.mingduo.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.mingduo.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码相关的扩展点配置。配置在这里的bean，业务系统都可以通过声明同类型或同名的bean来覆盖安全
 * 模块默认的配置。
 *
 * @author : weizc
 * @description:
 * @since 2019/10/24
 */
@Configuration
public class ValidateCodeBeanConfig {

    /**
     * 图片验证码图片生成器
     *
     * @return
     */
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    @Bean
    public ValidateCodeGenerator imageValidateCodeGenerator() {
        ImageCodeGenerator imageGenerator = new ImageCodeGenerator();
        return imageGenerator;
    }

    /**
     * 短信验证码发送器
     *
     * @return
     */
    @ConditionalOnMissingBean(SmsCodeSender.class)
    @Bean
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }
}
