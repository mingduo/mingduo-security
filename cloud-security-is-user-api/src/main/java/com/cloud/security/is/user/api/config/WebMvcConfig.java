package com.cloud.security.is.user.api.config;

import com.cloud.security.is.user.api.interceptor.AuditLogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 
 * @apiNode:
 * @since 2020/1/15
 * @author : weizc 
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    AuditLogInterceptor auditLogInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditLogInterceptor );
    }
}
