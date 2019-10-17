package com.mingduo.security.demo.web.controller;

import com.mingduo.security.demo.exception.UserNotExsitException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : weizc
 * @description:
 * @since 2019/10/16
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserNotExsitException.class)
    public Map<String, Object> handleUserNotExsitException(UserNotExsitException ex) {
        Map map = new HashMap(2);
        map.put("id", ex.getId());
        map.put("message", ex.getMessage());

        return map;
    }
}
