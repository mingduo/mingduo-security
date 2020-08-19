package com.mingduo.security.authorize.rbac;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @apiNode:
 * @since 2019/11/28
 * @author : weizc 
 */
public interface RbacService {
    /**
     * 判断的是否拥有权限
     * @param request
     * @param authentication
     * @return
     */
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
