package com.mingduo.security.core.validate.code.image;

import org.springframework.web.context.request.ServletWebRequest;

/**
 *
 * 校验码生成器
 * @since 2019/10/24
 * @author : weizc 
 */
public interface ValidateCodeGenerator {

    /**
     * 生成校验码
     * @param request
     * @return
     */
    ImageCode generate(ServletWebRequest request);
}
