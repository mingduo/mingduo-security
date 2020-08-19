package com.mingduo.security.core.validate.code.impl;

import com.mingduo.security.core.constants.ValidateCodeType;
import com.mingduo.security.core.validate.code.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;
import java.util.Objects;

/**
 * 抽象的图片验证码处理器
 *
 * @author : weizc
 * @since 2019/10/25
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {


    /**
     * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
     * <p>
     * 这是Spring开发的常见技巧，叫做定向查找（定向搜索）
     * <p>
     * Spring启动时，会查找容器中所有的ValidateCodeGenerator接口的实现，并把Bean的名字作为key，放到map中
     * 在我们这个系统中，ValidateCodeGenerator接口有两个实现，一个是ImageCodeGenerator，一个是SmsCodeGenerator，系统启动完成后，这个map中就会有2个bean，key分别是bean的名字
     * <p>
     * 生成验证码的时候，会根据请求的不同（有一个type值区分是获取短信验证码还是图片验证码），来获取短信验证码的生成器或者图形验证码的生成器
     */
    @Autowired
    Map<String, ValidateCodeGenerator> validateCodeGenerators;

    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    /**
     * @param request
     * @throws Exception
     */
    @Override
    public void create(ServletWebRequest request) throws Exception {
        // 生成
        C validateCode = generate(request);
        // 放到session
        save(request, validateCode);
        // 发送
        send(request, validateCode);

    }

    /**
     * 生成校验码
     *
     * @param request
     * @return
     */
    private C generate(ServletWebRequest request) {
        String type = getProcessorType(request);

        String generatorName = ValidateCodeGenerator.class.getSimpleName();

        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(type + generatorName);

        if (Objects.isNull(validateCodeGenerator)) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
        }
        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * 根据url 获取校验码的类型
     *
     * @param request
     * @return
     */
    private String getProcessorType(ServletWebRequest request) {
        String className = this.getClass().getSimpleName();
        return StringUtils.substringBefore(className, "CodeProcessor").toLowerCase();
    }

    /**
     * 发送校验码，由子类实现
     *
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    /**
     * 保存校验码
     *
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, C validateCode) {
        //设置session
        ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        ValidateCodeType codeType = ValidateCodeType.valueOf(getProcessorType(request).toUpperCase());
        validateCodeRepository.save(request,codeType,code);
    }


    @Override
    public void validate(ServletWebRequest request) throws AuthenticationException {
        ValidateCodeType validateCode = ValidateCodeType.valueOf(getProcessorType(request).toUpperCase());
        ValidateCode codeInSession = validateCodeRepository.get(request, validateCode);

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), validateCode.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }
        //验证码为空
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码为空");
        }
        if (Objects.isNull(codeInSession)) {
            throw new ValidateCodeException("验证码不存在");
        }

        if (codeInSession.isExpire()) {
            validateCodeRepository.remove(request,validateCode);
            throw new ValidateCodeException("验证码失效");
        }
        //验证码不匹配
        if (!StringUtils.equals(codeInRequest, codeInSession.getCode())) {
            throw new ValidateCodeException("验证码不匹配");
        }

        validateCodeRepository.remove(request,validateCode);
    }

}
