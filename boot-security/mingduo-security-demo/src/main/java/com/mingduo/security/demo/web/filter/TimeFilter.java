package com.mingduo.security.demo.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author : weizc
 * @description:
 * @since 2019/10/17
 */
//@Component
@Slf4j
public class TimeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("time filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        log.info("time filter start");
        long before = System.currentTimeMillis();
        chain.doFilter(servletRequest, servletResponse);
        System.out.printf("time filter 耗时:%d \n", System.currentTimeMillis() - before);
        log.info("time filter finish");

    }


    @Override
    public void destroy() {
        log.info("time filter destroy");

    }
}
