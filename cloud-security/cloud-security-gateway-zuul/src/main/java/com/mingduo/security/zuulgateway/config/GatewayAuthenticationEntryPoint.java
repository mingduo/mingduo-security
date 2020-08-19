package com.mingduo.security.zuulgateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.mingduo.security.zuulgateway.filter.AuditLogOncePerFilter.LOG_USER;

/**
 * 
 * @apiNode:
 * @since 2020/4/2
 * @author : weizc 
 */
@Slf4j
@Component
public class GatewayAuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        if(authException instanceof AccessTokenRequiredException){

            log.error("update log 401",authException);
        }else {
            log.error("add log 401",authException);
        }
        request.setAttribute(LOG_USER,true);

        super.commence(request, response, authException);
    }
}
