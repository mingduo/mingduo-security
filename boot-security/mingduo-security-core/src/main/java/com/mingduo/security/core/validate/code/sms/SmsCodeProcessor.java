package com.mingduo.security.core.validate.code.sms;

import com.mingduo.security.core.constants.SecurityConstants;
import com.mingduo.security.core.validate.code.ValidateCode;
import com.mingduo.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 *
 * 短信验证码处理器
 * @since 2019/10/25
 * @author : weizc 
 */
@Component("smsCodeValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor {
    /**
     * 短信验证码发送器
     */
    @Autowired
    SmsCodeSender smsCodeSender;

    @Override
    public void send(ServletWebRequest request, ValidateCode validateCode) throws ServletRequestBindingException {
        String paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
        smsCodeSender.send(mobile,validateCode.getCode());
    }



}
