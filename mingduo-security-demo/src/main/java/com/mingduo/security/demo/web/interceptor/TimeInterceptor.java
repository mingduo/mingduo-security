package com.mingduo.security.demo.web.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @description:
 * @since 2019/10/17
 * @author : weizc 
 */
public class TimeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle");

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        System.out.println(handlerMethod.getBean().getClass().getName());
        System.out.println(handlerMethod.getMethod().getName());

        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
        Long start= (Long) request.getAttribute("startTime");
        System.out.printf("TimeInterceptor 耗时:%s \n",System.currentTimeMillis()-start);

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion");
        Long start= (Long) request.getAttribute("startTime");
        System.out.printf("TimeInterceptor 耗时:%s \n",System.currentTimeMillis()-start);
        System.out.println("ex is "+ex);
    }
}
