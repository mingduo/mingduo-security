package com.mingduo.security.core.validate.code;

import com.mingduo.security.core.constants.SecurityConstants;
import com.mingduo.security.core.constants.ValidateCodeType;
import com.mingduo.security.core.properties.SecurityProperites;
import com.mingduo.security.core.properties.SmsCodeProperties;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 * 校验验证码的过滤器
 *
 * @author : weizc
 * @description:
 * @since 2019/10/24
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {
    /**
     * 验证码校验失败处理器
     */
    @Autowired
    AuthenticationFailureHandler authenticationFailureHandler;
    /**
     * 系统配置信息
     */
    @Autowired
    SecurityProperites securityProperites;
    /**
     * 系统中的校验码处理器
     */
    @Autowired
    ValidateCodeProcessorHolder validateCodeProcessorHolder;
    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    /**
     * 存放所有需要校验验证码的url
     */
    Map<String, ValidateCodeType> urlMaps = new HashMap<>(10);


    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        Optional<ValidateCodeType> validateCode = getValidateCodeType(request);

        if (validateCode.isPresent()) {
            try {
                logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + validateCode);

                validateCodeProcessorHolder.findValidateProcessor(validateCode.get())
                        .validate(new ServletWebRequest(request, response));
                logger.info("验证码校验通过");


            } catch (AuthenticationException e) {
                logger.error("验证码校验失败:{}", e);

                authenticationFailureHandler.onAuthenticationFailure(request, response,  e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     *
     * @param request
     * @return
     */
    private Optional<ValidateCodeType> getValidateCodeType(HttpServletRequest request) {
        return urlMaps.entrySet().stream().filter(entry ->
                antPathMatcher.match(entry.getKey(), request.getRequestURI()) &&
                        !StringUtils.equals(request.getMethod(), HttpMethod.GET.name()))
                .map(Map.Entry::getValue).findFirst();
    }

    /**
     * 初始化要拦截的url配置信息
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        urlMaps.put(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM,ValidateCodeType.IMAGE);
        addUrlToMap(securityProperites.getCode().getImage(), ValidateCodeType.IMAGE);

        urlMaps.put(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE,ValidateCodeType.SMS);
        addUrlToMap(securityProperites.getCode().getSms(), ValidateCodeType.SMS);

    }
    /**
     * 讲系统中配置的需要校验验证码的URL根据校验的类型放入map
     *
     * @param sms
     * @param validateCode
     */
    private void addUrlToMap(SmsCodeProperties sms, ValidateCodeType validateCode) {
        String[] smsUrls = StringUtils.splitByWholeSeparator(sms.getUrl(), ",");
        if (ArrayUtils.isNotEmpty(smsUrls)) {
            Arrays.stream(smsUrls).forEach(url -> urlMaps.put(url, validateCode));
        }
    }


}
