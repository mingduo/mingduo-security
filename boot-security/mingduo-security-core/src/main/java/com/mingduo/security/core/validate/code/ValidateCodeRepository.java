package com.mingduo.security.core.validate.code;

import com.mingduo.security.core.constants.ValidateCodeType;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码存取器
 * @since 2019/11/13
 * @author: weizc
 */
public interface ValidateCodeRepository {
    /**
     * 保存验证码
     * @param request
     * @param code
     * @param codeType
     */
   void save(ServletWebRequest request, ValidateCodeType codeType, ValidateCode code);
    /**
     * 获取验证码
     * @param request
     * @param codeType
     * @return
     */
    void remove(ServletWebRequest request,ValidateCodeType codeType);
    /**
     * 移除验证码
     * @param request
     * @param codeType
     */
    ValidateCode get(ServletWebRequest request,ValidateCodeType codeType);

}
