package com.mingduo.security.core.social.support;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * SocialAuthenticationFilter后处理器，用于在不同环境下个性化社交登录的配置
 *
 * @since  2019/11/16
 * @author: weizc
 */
public interface SocialAuthenticationFilterPostProcessor {

    void process(SocialAuthenticationFilter socialAuthenticationFilter);
}
