package com.mingduo.security.demo.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * 
 * @description:
 * @since 2019/10/17
 * @author : weizc 
 */
//@Component
public class TimeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        System.out.println("time filter start");
        long before = System.currentTimeMillis();
        chain.doFilter(servletRequest, servletResponse);
        System.out.printf("time filter 耗时:%d \n",System.currentTimeMillis()-before);
        System.out.println("time filter finish");

    }


    @Override
    public void destroy() {
        System.out.println("destroy");

    }
}
