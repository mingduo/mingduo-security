package com.mingduo.security.core.validate.code.sms;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * 验证码信息封装类
 * @since 2019/10/25
 * @author : weizc 
 */
@Data
public class ValidateCode implements Serializable {

    //验证码
    private String code;

    private LocalDateTime expireTime;

    public boolean isExpire(){
        return LocalDateTime.now().isAfter(expireTime);
    }


    public ValidateCode(String code, long expireSeconds) {
        this.code = code;
        this.expireTime=LocalDateTime.now().plusSeconds(expireSeconds);
    }
}
