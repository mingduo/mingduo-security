package com.mingduo.security.core.authentication.mobile;

import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Objects;

/**
 *
 * 短信登录验证逻辑
 *
 * 由于短信验证码的验证在过滤器里已完成，这里直接读取用户信息即可。
 *
 * @author : weizc
 * @description:
 * @since 2019/10/28
 */
@Data
public class SmsAuthenticationProvider implements AuthenticationProvider {

    UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        SmsAuthenticationToken smsAuthenticationToken= (SmsAuthenticationToken) authentication;

        UserDetails user = userDetailsService.loadUserByUsername(smsAuthenticationToken.getName());

        if(Objects.isNull(user)){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        SmsAuthenticationToken result = new SmsAuthenticationToken(
                user, user.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class
                .isAssignableFrom(authentication);
    }



}
