package com.mingduo.security.core.constants;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * @description:
 * @since 2019/10/25
 * @author : weizc 
 */
public interface SecurityConstants {

    /**
     * 默认的处理验证码的url前缀
     */
    String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";
    /**
     * 当请求需要身份认证时，默认跳转的url
     *
     * @see BrowerSecurityController
     */
    String DEFAULT_LOGIN_PAGE="/authentication/require";
    /**
     * 默认的用户名密码登录请求处理url
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL_FORM="/authentication/form";

    /**
     * 默认的手机验证码登录请求处理url
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE = "/authentication/mobile";
    /**
     * 默认的OPENID登录请求处理url
     */
    String DEFAULT_SIGN_IN_PROCESSING_URL_OEPNID = "/authentication/openId";

    /**
     * 默认登录页面
     *
     * @see BrowerSecurityController
     */
    String DEFAULT_SIGN_IN_PAGE_URL = "/browser-signIn.html";

    /**
     * 获取第三方用户信息的url
     */
    String DEFAULT_SOCIAL_USER_INFO_URL = "/social/user";
    /**
     * session失效默认的跳转地址
     */
    String DEFAULT_SESSION_INVALID_URL="/browser-session-invalid.html";

    /**
     * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";
    /**
     * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";
    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";
    /**
     *
     */
    List<String> ADMIN_ROLE_LIST= Arrays.asList("mingduo","weizichao");

    List<String> USER_ROLE_LIST= Arrays.asList("user","123");

}
