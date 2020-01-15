package com.cloud.security.is.user.api.filter;

import com.cloud.security.is.user.api.user.User;
import com.cloud.security.is.user.api.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaworks.crypto.SCryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/1/15
 */
@Order(2)
@Component
public class HttpBasicAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(header) && header.startsWith("Basic ")) {

            String token64 = StringUtils.substringAfter(header, "Basic ");
            String token = new String(Base64.getDecoder().decode(token64));

            String[] items = StringUtils.splitByWholeSeparator(token, ":");

            String username = items[0];
            String password = items[1];
            Optional<User> userinfo = userRepository.findByUsername(username);
            //登录成功
            if (userinfo.isPresent() && SCryptUtil.check(password, userinfo.get().getPassword())) {

                request.getSession().setAttribute("user", userinfo.get().buildUserInfo());
                request.getSession().setAttribute("tmp", "yes");

            }
        }
        try {
            filterChain.doFilter(request, response);

        } finally {

        }

    }
}
