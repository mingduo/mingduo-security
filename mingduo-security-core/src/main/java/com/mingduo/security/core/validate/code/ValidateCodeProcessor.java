package com.mingduo.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码处理器，封装不同校验码的处理逻辑
 *
 * @author : weizc
 * @description:
 * @since 2019/10/25
 */
public interface ValidateCodeProcessor {

    String SEESSION_KEY = "SEESSION_KEY_VALIDATE_CODE_";

    /**
     * 创建校验码
     *
     * @param request
     */
    void create(ServletWebRequest request) throws Exception;

    /**
     * 校验
     *
     * @param request
     * @throws Exception
     */
    void validate(ServletWebRequest request) throws AuthenticationException;
}
