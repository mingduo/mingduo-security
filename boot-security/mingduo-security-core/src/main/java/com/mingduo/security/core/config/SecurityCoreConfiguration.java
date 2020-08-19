package com.mingduo.security.core.config;

import com.mingduo.security.core.properties.SecurityProperites;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @description:
 * @since 2019/10/21
 * @author : weizc 
 */
@Configuration
@EnableConfigurationProperties(SecurityProperites.class)
public class SecurityCoreConfiguration {
}
