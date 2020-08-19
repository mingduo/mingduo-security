package com.cloud.security.is.user.api.interceptor;

import com.cloud.security.is.user.api.user.UserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @apiNode:
 * @since 2020/1/15
 * @author : weizc 
 */
@Component
public class AclInterceptor implements HandlerInterceptor {

    List<String> permitUrls= Collections.unmodifiableList(Collections.singletonList("/users/login"));

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result=true;

        String requestURI = request.getRequestURI();

        if(permitUrls.contains(requestURI)){
            UserInfo user = (UserInfo) request.getSession().getAttribute("user");
            if(user==null){
                getResponse(response, HttpStatus.UNAUTHORIZED);
                result=false;
            }else {

                if(!user.hasPermission(request.getMethod())){
                    getResponse(response, HttpStatus.FORBIDDEN);
                    result=false;

                }
            }
        }

        return result;
    }

    private void getResponse(HttpServletResponse response, HttpStatus forbidden) throws IOException {
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.getWriter().write(forbidden.getReasonPhrase());
        response.setStatus(forbidden.value());
    }




}
