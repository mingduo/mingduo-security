package com.mingduo.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 安全配置项
 *
 * @author : weizc
 * @description:
 * @since 2019/10/21
 */
@Data
@Configuration
@ConfigurationProperties("my.security")
public class SecurityProperites {
    /**
     * 浏览器配置项
     */
    BrowserProperties browser = new BrowserProperties();
    /**
     * 验证码配置
     */
    ValidateCodeProperties code = new ValidateCodeProperties();
    /**
     * 社交登录配置
     */
    SocialProperties social = new SocialProperties();
    /**
     * OAuth2认证服务器配置
     */
    OAuth2Properties oauth2 =new OAuth2Properties();
}
