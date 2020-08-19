package com.mingduo.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * 
 * @description:
 * @since 2019/10/24
 * @author : weizc 
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
