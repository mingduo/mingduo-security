package com.mingduo.security.core.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * 默认社交登录实现
 * @apiNode:
 * @since 2019/11/29
 * @author : weizc 
 */
@Slf4j
public class DefaultSocialUserDetailsService extends BaseUserService implements SocialUserDetailsService {

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        log.info("登录userId:{}", userId);
        return buildUser(userId);
    }
}
