package com.mingduo.security.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author : weizc
 * @description:
 * @since 2019/10/18
 */
@Slf4j
@Component
public class MyUserDetailService implements UserDetailsService, SocialUserDetailsService {

    List<String>adminRoleList= Arrays.asList("mingduo","weizichao");
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public SocialUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("登录用户名:{}", username);
        // 根据用户名查找用户信息
        //根据查找到的用户信息判断用户是否被冻结
        return buildUser(username);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        log.info("登录userId:{}", userId);
        return buildUser(userId);
    }

    private SocialUserDetails buildUser(String userId) {
        // 根据用户名查找用户信息
        //根据查找到的用户信息判断用户是否被冻结
        String password = passwordEncoder.encode("123");
        log.info("数据库密码:{}", password);
        String roleString = "ROLE_USER";
        if (adminRoleList.contains(userId)) {
            roleString = "ROLE_ADMIN,ROLE_USER";
        }
        return new SocialUser(userId, password,
                AuthorityUtils.commaSeparatedStringToAuthorityList(roleString));
    }
}
