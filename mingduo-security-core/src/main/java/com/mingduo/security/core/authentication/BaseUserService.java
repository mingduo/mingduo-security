package com.mingduo.security.core.authentication;

import com.mingduo.security.core.constants.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;

/**
 * 
 * @apiNode:
 * @since 2019/11/29
 * @author : weizc 
 */
@Slf4j
public class BaseUserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    protected SocialUserDetails buildUser(String userId) {
        // 根据用户名查找用户信息
        //根据查找到的用户信息判断用户是否被冻结
        String password = passwordEncoder.encode("123");
        log.info("数据库密码:{}", password);
        String roleString = "ROLE_USER";
        if (SecurityConstants.ADMIN_ROLE_LIST.contains(userId)) {
            roleString = "ROLE_ADMIN,ROLE_USER";
        }
        return new SocialUser(userId, password,
                AuthorityUtils.commaSeparatedStringToAuthorityList(roleString));
    }
}
