package com.mingduo.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mingduo.security.core.properties.LoginResponseType;
import com.mingduo.security.core.properties.SecurityProperites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 成功处理器
 *
 * @author : weizc
 * @description:
 * @since 2019/10/22
 */
@Component
public class BrowserAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private SecurityProperites securityProperites;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LoginResponseType signInResponseType = securityProperites.getBrowser().getSignInResponseType();

        logger.info("登录成功");
        if (LoginResponseType.JSON.equals(signInResponseType)) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            PrintWriter writer = response.getWriter();
            writer.println(objectMapper.writeValueAsString(ResponseEntity.ok(authentication.getPrincipal())));
            writer.flush();
        } else {
            // 否则跳转页面
            // 如果设置了imooc.security.browser.singInSuccessUrl，总是跳到设置的地址上
            // 如果没设置，则尝试跳转到登录之前访问的地址上，如果登录前访问地址为空，则跳到网站根路径上


            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
