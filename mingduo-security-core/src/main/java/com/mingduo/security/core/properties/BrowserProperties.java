package com.mingduo.security.core.properties;

import com.mingduo.security.core.constants.SecurityConstants;
import lombok.Data;

/**
 * 浏览器环境配置项
 *
 * @description:
 * @since 2019/10/21
 * @author : weizc 
 */
@Data
public class BrowserProperties {

    /**
     * 登录页面，当引发登录行为的url以html结尾时，会跳到这里配置的url上
     */
    private String signInPage= SecurityConstants.DEFAULT_SIGN_IN_PAGE_URL;

    /**
     * 社交登录，如果需要用户注册，跳转的页面
     */
    private String signUpUrl = "/browser-signUp.html";
    /**
     * 登录响应的方式，默认是json
     */
    private LoginResponseType signInResponseType=LoginResponseType.JSON;
    /**
     * '记住我'功能的有效时间，默认1小时
     */
    private int rememberMeSeconds = 3600;
}
