package com.mingduo.security.core.authorization;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 *
 * 授权信息管理器
 *
 * 用于收集系统中所有 AuthorizeConfigProvider 并加载其配置
 * @since 2019/11/28
 * @author : weizc 
 */
public interface AuthorizeConfigManager {

    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry);

}
