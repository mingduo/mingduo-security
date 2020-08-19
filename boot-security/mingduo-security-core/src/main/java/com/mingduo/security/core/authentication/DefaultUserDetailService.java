package com.mingduo.security.core.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;

/**
 *
 * 默认 表单登录
 * @author : weizc
 * @description:
 * @since 2019/10/18
 */
@Slf4j
public class DefaultUserDetailService extends BaseUserService implements UserDetailsService {


    @Override
    public SocialUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("登录用户名:{}", username);
        // 根据用户名查找用户信息
        //根据查找到的用户信息判断用户是否被冻结
        return buildUser(username);
    }




}
