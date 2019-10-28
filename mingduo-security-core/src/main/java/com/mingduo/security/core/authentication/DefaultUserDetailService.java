package com.mingduo.security.core.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * 默认的 UserDetailsService 实现
 *
 * 不做任何处理，只在控制台打印一句日志，然后抛出异常，提醒业务系统自己配置 UserDetailsService。
 * @since 2019/10/28
 * @author : weizc 
 */
@Slf4j
public class DefaultUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       log.warn("请配置 UserDetailsService 的实现");
       throw new UsernameNotFoundException(username);
    }
}
