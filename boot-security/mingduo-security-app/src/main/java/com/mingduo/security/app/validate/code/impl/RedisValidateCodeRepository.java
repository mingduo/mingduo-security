package com.mingduo.security.app.validate.code.impl;

import com.mingduo.security.core.constants.ValidateCodeType;
import com.mingduo.security.core.validate.code.ValidateCode;
import com.mingduo.security.core.validate.code.ValidateCodeException;
import com.mingduo.security.core.validate.code.ValidateCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 基于redis的验证码存取器，避免由于没有session导致无法存取验证码的问题
 *
 * @author :    weizc
 * @since 2019/11/13
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void save(ServletWebRequest request, ValidateCodeType codeType, ValidateCode code) {
        String key = buildKey(request, codeType);
        redisTemplate.opsForValue().set(key, code, 30, TimeUnit.MINUTES);
    }


    @Override
    public void remove(ServletWebRequest request, ValidateCodeType codeType) {
        redisTemplate.delete(buildKey(request, codeType));
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType codeType) {
        return (ValidateCode) Optional.ofNullable(redisTemplate.opsForValue()
                .get(buildKey(request, codeType))).orElse(null);

    }

    /**
     * @param request
     * @param codeType
     * @return
     */
    private String buildKey(ServletWebRequest request, ValidateCodeType codeType) {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.hasText(deviceId)) {
            return "code:" + codeType.name() + ":" + deviceId;
        }
        throw new ValidateCodeException("请求中deviceId为空");

    }
}
