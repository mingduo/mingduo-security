package com.mingduo.security.ssoserver.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 
 * @description:
 * @since 2019/11/23
 * @author : weizc 
 */
@Component
public class SsoUserDetailService implements UserDetailsService {

@Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password = passwordEncoder.encode("123");
        return User.withUsername(username).password(password).roles("ROLE_USER").build();
    }
}
