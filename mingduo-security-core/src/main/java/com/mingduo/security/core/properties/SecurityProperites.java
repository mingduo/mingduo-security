package com.mingduo.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *  安全配置项
 * @description:
 * @since 2019/10/21
 * @author : weizc 
 */
@Data
@Configuration
@ConfigurationProperties("my.security")
public class SecurityProperites {
    /**
     * 浏览器配置项
     */
    BrowserProperties browser=new BrowserProperties();
}
