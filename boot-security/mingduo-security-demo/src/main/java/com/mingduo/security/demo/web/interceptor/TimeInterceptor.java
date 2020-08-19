package com.mingduo.security.demo.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : weizc
 * @description:
 * @since 2019/10/17
 */
@Slf4j
public class TimeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle");
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            log.info(handlerMethod.getBean().getClass().getName());
            log.info(handlerMethod.getMethod().getName());
        }
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
         log.info("postHandle");
        Long start = (Long) request.getAttribute("startTime");
        System.out.printf("TimeInterceptor 耗时:%s \n", System.currentTimeMillis() - start);

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
         log.info("afterCompletion");
        Long start = (Long) request.getAttribute("startTime");
        System.out.printf("TimeInterceptor 耗时:%s \n", System.currentTimeMillis() - start);
         log.info("ex is " + ex);
    }
}
