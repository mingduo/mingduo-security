package com.mingduo.security.browser.controller;

import com.mingduo.security.core.constants.SecurityConstants;
import com.mingduo.security.core.properties.SecurityProperites;
import com.mingduo.security.core.social.controller.SocialController;
import com.mingduo.security.core.social.support.SocialUserInfo;
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
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 浏览器环境下与安全相关的服务
 * @since 2019/10/21
 * @author : weizc 
 */
@Slf4j
@RestController
public class BrowserSecurityController extends SocialController {

    private RequestCache requestCache=new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperites securityProperites;

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    /**
     * 当需要身份认证时，跳转到这里
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @RequestMapping(SecurityConstants.DEFAULT_LOGIN_PAGE)
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

    /**
     * 用户第一次社交登录时，会引导用户进行用户注册或绑定，此服务用于在注册或绑定页面获取社交网站用户信息
     *
     * @param request
     * @return
     */
    @GetMapping(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL)
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request){
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));

        return buildSocialUserInfo(connection);
    }


    @GetMapping("/session/invalid")
    public ResponseEntity invalidSession(){
        return new ResponseEntity("session失效", HttpStatus.UNAUTHORIZED);
    }
}
