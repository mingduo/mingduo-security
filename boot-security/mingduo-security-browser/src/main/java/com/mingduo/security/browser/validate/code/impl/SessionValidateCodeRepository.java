package com.mingduo.security.browser.validate.code.impl;

import com.mingduo.security.core.constants.ValidateCodeType;
import com.mingduo.security.core.validate.code.ValidateCode;
import com.mingduo.security.core.validate.code.ValidateCodeRepository;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 基于session的验证码存取器
 *
 * @author :    weizc
 * @since 2019/11/13
 */
@Component
public class SessionValidateCodeRepository implements ValidateCodeRepository {
    /**
     * 验证码放入session时的前缀
     */
    String SEESSION_KEY = "SEESSION_KEY_VALIDATE_CODE_";

    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();
    @Override
    public void save(ServletWebRequest request, ValidateCodeType codeType, ValidateCode code) {
        sessionStrategy.setAttribute(request, SEESSION_KEY + codeType.name(), code);
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType codeType) {
        sessionStrategy.removeAttribute(request, SEESSION_KEY + codeType.name());
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType codeType) {
        return (ValidateCode) sessionStrategy.getAttribute(request,SEESSION_KEY + codeType.name());
    }
}
