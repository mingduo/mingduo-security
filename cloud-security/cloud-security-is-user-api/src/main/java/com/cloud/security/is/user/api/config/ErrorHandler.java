package com.cloud.security.is.user.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;

/**
 * 
 * @apiNode:
 * @since 2020/1/15
 * @author : weizc 
 */
@Slf4j
@RestControllerAdvice
public class ErrorHandler {


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler( Exception.class)
    public HashMap<String, Object> errror(Exception ex){
        HashMap<String, Object> result = new HashMap<>();

        result.put("message",ex.getMessage());
        result.put("time",new Date());

        return result;

    }
}
