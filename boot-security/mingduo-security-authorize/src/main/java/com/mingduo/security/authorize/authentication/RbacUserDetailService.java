package com.mingduo.security.authorize.authentication;

import com.mingduo.security.authorize.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 
 * @apiNode:
 * @since 2019/11/28
 * @author : weizc 
 */
@Component
public class RbacUserDetailService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findAdminByUsername(username);
    }


}
