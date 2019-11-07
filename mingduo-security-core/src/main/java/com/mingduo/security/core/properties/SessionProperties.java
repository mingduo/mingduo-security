package com.mingduo.security.core.properties;

import com.mingduo.security.core.constants.SecurityConstants;
import lombok.Data;

/**
 * 
 * @description:
 * @since 2019/11/7
 * @author : weizc 
 */
@Data
public class SessionProperties {
    /**
     * session失效时跳转的地址
     */
    String sessionInvalidUrl= SecurityConstants.DEFAULT_SESSION_INVALID_URL;
    /**
     * 同一个用户在系统中的最大session数，默认1
     */
    int maximumSessions=1;
    /**
     * 达到最大session时是否阻止新的登录请求，默认为false，不阻止，新的登录会将老的登录失效掉
     */
    boolean maxSessionsPreventsLogin;

}
