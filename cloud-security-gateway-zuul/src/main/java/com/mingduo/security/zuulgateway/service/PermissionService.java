package com.mingduo.security.zuulgateway.service;


import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * @apiNode:
 * @since 2020/3/26
 * @author : weizc 
 */
public interface PermissionService {

    boolean hasPermission(Authentication authentication, HttpServletRequest request);
}
