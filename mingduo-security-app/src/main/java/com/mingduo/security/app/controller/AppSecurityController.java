package com.mingduo.security.app.controller;

import com.mingduo.security.app.social.AppSignUpUtils;
import com.mingduo.security.core.constants.SecurityConstants;
import com.mingduo.security.core.social.controller.SocialController;
import com.mingduo.security.core.social.support.SocialUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : weizc
 * @description:
 * @since 2019/11/20
 */
@RestController
public class AppSecurityController extends SocialController {

    @Autowired
    ProviderSignInUtils providerSignInUtils;

    @Autowired
    AppSignUpUtils appSignUpUtils;

    /**
     * 用户第一次社交登录时，会引导用户进行用户注册或绑定，此服务用于在注册或绑定页面获取社交网站用户信息
     *
     * @param request
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @GetMapping(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL)
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));

        appSignUpUtils.saveConnectData(connection.createData(),new ServletWebRequest(request));

        return buildSocialUserInfo(connection);


    }

}
