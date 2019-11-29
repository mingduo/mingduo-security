package com.mingduo.security.authorize.service;

import com.mingduo.security.authorize.domain.Admin;
import com.mingduo.security.authorize.rbac.RbacService;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * 
 * @apiNode:
 * @since 2019/11/28
 * @author : weizc 
 */
@Component("rbacService")
public class RbacSeviceImpl implements RbacService {

    /**
     * 判断是否匹配
     */
    private AntPathMatcher antPathMatcher=new AntPathMatcher();

    /**
     * 参数为什么注入 ？
     * 属性来源
     * @see SecurityExpressionRoot
     * @see org.springframework.security.web.access.expression.WebSecurityExpressionRoot
     * 属性查找
     * @see org.springframework.expression.spel.support.ReflectivePropertyAccessor
     *
     * @param request
     * @param authentication
     * @return
     */
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Admin admin = (Admin) authentication.getPrincipal();
        Set<String> rbacUrls = admin.getUrls();

        boolean match = rbacUrls.stream().anyMatch(url -> antPathMatcher.match(url, request.getRequestURI()));
        return match;
    }
}
