package com.mingduo.security.browser.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mingduo.security.core.properties.SecurityProperites;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认的退出成功处理器，如果设置了imooc.security.browser.signOutUrl，则跳到配置的地址上，
 * 如果没配置，则返回json格式的响应。
 *
 * @since 2019/11/7
 * @author : weizc 
 */
@Slf4j
public class BrowserLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    ObjectMapper objectMapper;

    private final SecurityProperites securityProperites;

    public BrowserLogoutSuccessHandler(SecurityProperites securityProperites) {
        this.securityProperites = securityProperites;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("退出成功");


        if(StringUtils.isNotBlank(securityProperites.getBrowser().getSignOutPage())){
            response.sendRedirect(securityProperites.getBrowser().getSignOutPage());
        }else {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            ResponseEntity<String> entity = ResponseEntity.ok("退出成功");
            response.getWriter().write(objectMapper.writeValueAsString(entity));
        }
    }
}
