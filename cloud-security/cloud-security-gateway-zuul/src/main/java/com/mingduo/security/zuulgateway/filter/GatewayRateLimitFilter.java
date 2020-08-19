package com.mingduo.security.zuulgateway.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.RateLimitUtils;
import org.apache.http.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/2/28
 */
public class GatewayRateLimitFilter extends OncePerRequestFilter {

    RateLimiter limiter = RateLimiter.create(1);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (limiter.tryAcquire()) {
            filterChain.doFilter(request, response);
        } else {
            PrintWriter writer = response.getWriter();
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            writer.write("too many request");
            writer.flush();

        }
    }
}
