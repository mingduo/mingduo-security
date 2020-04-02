package com.mingduo.security.zuulgateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/2/28
 */
@Slf4j
public class AuditLogOncePerFilter extends OncePerRequestFilter {
    public static final String LOG_USER="LOG_USER";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        Object principal = securityContext.getAuthentication().getPrincipal();

        log.info("user:{} do log", principal);


        filterChain.doFilter(request, response);

        if(request.getAttribute(LOG_USER)!=null){
            log.info("update log success");
        }
    }
}
