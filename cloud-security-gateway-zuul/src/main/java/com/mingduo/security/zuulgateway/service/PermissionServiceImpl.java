package com.mingduo.security.zuulgateway.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 *
 * @author weizc
 */
@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private Random r=new Random();
    @Override
    public boolean hasPermission(Authentication authentication, HttpServletRequest request) {


        System.out.println("request uri : " + UrlUtils.buildRequestUrl(request));
        System.out.println("authentication :" + ReflectionToStringBuilder.toString(authentication, ToStringStyle.MULTI_LINE_STYLE));

        int num = r.nextInt(10);

        if (num<5) {
            throw new AuthenticationServiceException("随机访问失败");
        }
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new AuthenticationServiceException("匿名的用户登录");
        }
        return true;
    }

}




