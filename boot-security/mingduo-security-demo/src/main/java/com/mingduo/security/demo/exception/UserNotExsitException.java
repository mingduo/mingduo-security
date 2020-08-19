package com.mingduo.security.demo.exception;

import lombok.Data;

/**
 * 
 * @description:
 * @since 2019/10/16
 * @author : weizc 
 */
@Data
public class UserNotExsitException extends RuntimeException{

    String id;

    public UserNotExsitException(String id) {
        super("user not exist");
        this.id = id;
    }
}
