package com.cloud.security.is.user.api.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
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
 * @since 2020/1/14
 */
@Component
public class RateLimitFilter extends OncePerRequestFilter {

    RateLimiter rateLimiter = RateLimiter.create(1);
    @Autowired
    ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (rateLimiter.tryAcquire()) {
            filterChain.doFilter(request, response);
        } else {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            ResponseEntity<String> responseEntity = ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("to many request");
            PrintWriter responseWriter = response.getWriter();
            responseWriter.write(objectMapper.writeValueAsString(responseEntity));
            responseWriter.flush();
        }
    }
}
