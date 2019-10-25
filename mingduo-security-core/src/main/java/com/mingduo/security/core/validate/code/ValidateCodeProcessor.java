package com.mingduo.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码处理器，封装不同校验码的处理逻辑
 * @description:
 * @since 2019/10/25
 * @author : weizc 
 */
public interface ValidateCodeProcessor {

    /**
     * 	 创建校验码
     * @param request
     */
    void create(ServletWebRequest request) throws Exception;
}
