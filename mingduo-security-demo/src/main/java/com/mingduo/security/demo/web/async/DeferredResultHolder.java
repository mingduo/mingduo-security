package com.mingduo.security.demo.web.async;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @description:
 * @since 2019/10/17
 * @author : weizc 
 */
@Data
@Component
public class DeferredResultHolder {

    private Map<String, DeferredResult<String>> map=new ConcurrentHashMap<>();

}
