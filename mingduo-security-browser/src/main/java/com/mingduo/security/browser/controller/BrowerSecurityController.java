package com.mingduo.security.browser.controller;

import com.mingduo.security.core.properties.SecurityProperites;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 
 * @description:
 * @since 2019/10/21
 * @author : weizc 
 */
@Slf4j
@RestController
public class BrowerSecurityController {

    private RequestCache requestCache=new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperites securityProperites;

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @RequestMapping("/authentication/require")
    public ResponseEntity requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if(savedRequest!=null) {
            String targetUrl = savedRequest.getRedirectUrl();
            log.info("引发跳转的url:{}",targetUrl);
            if(StringUtils.endsWithIgnoreCase(targetUrl, ".html")){
                redirectStrategy.sendRedirect(request, response, securityProperites.getBrowser().getSignInPage());
            }
        }
        return ResponseEntity.ok("访问的服务需要身份认证，请引导用户到登录页");
    }
}
