package com.mingduo.security.core.validate.code;

import com.mingduo.security.core.constants.ValidateCodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * 校验码处理器管理器
 * @since 2019/10/28
 * @author : weizc 
 */
@Component
public class ValidateCodeProcessorHolder {
    /**
     * 依赖搜索
     *
     * Spring启动时，会查找容器中所有的ValidateCodeProcessor接口的实现，并把Bean的名字作为key，放到map中
     */
    @Autowired
    Map<String, ValidateCodeProcessor> validateCodeProcessoros;
    /**
     * @param type
     * @return
     */
    public ValidateCodeProcessor findValidateProcessor(ValidateCodeType type){
        String paramNameOnValidate = type.getParamNameOnValidate();
        String name=paramNameOnValidate + ValidateCodeProcessor.class.getSimpleName();
        ValidateCodeProcessor validateCodeProcessor = validateCodeProcessoros.get(name);

        if(validateCodeProcessor==null){
            throw new ValidateCodeException("验证码处理器" + name + "不存在");
        }
        return validateCodeProcessor;

    }

}
