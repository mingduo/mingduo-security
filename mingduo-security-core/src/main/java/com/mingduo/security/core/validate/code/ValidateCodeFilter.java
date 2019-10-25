package com.mingduo.security.core.validate.code;

import com.mingduo.security.core.properties.SecurityProperites;
import com.mingduo.security.core.validate.code.image.ImageCode;
import com.mingduo.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author : weizc
 * @description:
 * @since 2019/10/24
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    @Autowired
    AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    SecurityProperites securityProperites;


    SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    AntPathMatcher antPathMatcher = new AntPathMatcher();
    //存放 验证码 需要拦截的url
    Set<String> urls = new HashSet<>(10);

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        boolean matchUrl = urls.stream().anyMatch(url ->
                antPathMatcher.match(url, request.getRequestURI()) &&
                        !StringUtils.equals(request.getMethod(), HttpMethod.GET.name()));

        if (matchUrl) {
            try {

                validate(new ServletWebRequest(request, response));
                logger.info("验证码校验通过");


            } catch (ValidateCodeException e) {
                logger.error("验证码校验失败:{}", e);

                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validate(ServletWebRequest webRequest) throws ServletRequestBindingException {

        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(webRequest, AbstractValidateCodeProcessor.SEESSION_KEY);

        String codeInRequest = ServletRequestUtils.getStringParameter(webRequest.getRequest(), "imageCode");
        //验证码为空
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码为空");
        }
        if (Objects.isNull(codeInSession)) {
            throw new ValidateCodeException("验证码不存在");
        }

        if (codeInSession.isExpire()) {
            sessionStrategy.removeAttribute(webRequest, AbstractValidateCodeProcessor.SEESSION_KEY);
            throw new ValidateCodeException("验证码失效");
        }
        //验证码不匹配
        if (!StringUtils.equals(codeInRequest, codeInSession.getCode())) {
            throw new ValidateCodeException("验证码不匹配");
        }

        sessionStrategy.removeAttribute(webRequest, AbstractValidateCodeProcessor.SEESSION_KEY);

    }


    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        urls.add("/authentication/form");
        String[] imageUrls = StringUtils.splitByWholeSeparator(securityProperites.getCode().getImage().getUrl(), ",");
        if(ArrayUtils.isNotEmpty(imageUrls)){
            Collections.addAll(urls, imageUrls);
        }

    }


}
