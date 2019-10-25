package com.mingduo.security.core.validate.code.sms;

import com.mingduo.security.core.properties.SecurityProperites;
import com.mingduo.security.core.validate.code.ValidateCodeGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 *
 * 短信验证码生成器
 * @since 2019/10/25
 * @author : weizc 
 */
@Component("smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    SecurityProperites securityProperites;

    /**
     *
     * @param request
     * @return
     */
    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperites.getCode().getSms().getLength());
        return new ValidateCode(code, securityProperites.getCode().getSms().getExpireIn());
    }
}
