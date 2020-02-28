package com.mingduo.security.zuulgateway.filter.zuul;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.DefaultRateLimiterErrorHandler;
import org.springframework.stereotype.Component;

/**
 * 
 * @apiNode:
 * @since 2020/1/20
 * @author : weizc 
 */
@Component
public class MyRatelimitErrorHandler extends DefaultRateLimiterErrorHandler {

    @Override
    public void handleError(String msg, Exception e) {
        super.handleError(msg, e);
    }
}
