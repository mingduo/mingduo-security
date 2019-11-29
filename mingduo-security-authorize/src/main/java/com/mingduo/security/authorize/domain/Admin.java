package com.mingduo.security.authorize.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @apiNode:
 * @since 2019/11/28
 * @author : weizc 
 */
@Data
public class Admin extends User {

    /**
     * 用户有权访问的所有url，不持久化到数据库
     */
    private Set<String> urls = new HashSet<>();
    /**
     * 用户有权的所有资源id，不持久化到数据库
     */
    private Set<Long> resourceIds = new HashSet<>();

    public Admin(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }


}
