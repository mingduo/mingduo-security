package com.mingduo.security.core.social.qq.connect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author : weizc
 * @description:
 * @since 2019/11/1
 */
@Slf4j
public class QQOauth2Template extends OAuth2Template {

    ObjectMapper objectMapper = new ObjectMapper();

    public QQOauth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
    }

    public QQOauth2Template(String clientId, String clientSecret, String authorizeUrl, String authenticateUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, authenticateUrl, accessTokenUrl);
        /**
         * 发请求的时候携带参数
         */
        setUseParametersForClientAuthentication(true);

    }


    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        /**
         * 在原有的实现之上添加了一个能够处理text/html信息的converter
         */
        messageConverters.add(new StringHttpMessageConverter(Charset.defaultCharset()));
        return restTemplate;
    }

    /**
     * 处理QQ特殊的返回成功信息,不是json的
     *
     * @param accessTokenUrl
     * @param parameters
     * @return
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String result = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        log.info("获取access_token result:" + result);
        /**
         * access_token=FE04************************CCE2&expires_in=7776000
         * &refresh_token=88E4************************BE14
         *
         * 截取认证成功后的信息
         */
        String[] items = StringUtils.splitByWholeSeparator(result, "&");

        String accessToken = StringUtils.substringAfter(items[0], "access_token=");
        String expiresIn = StringUtils.substringAfter(items[1], "expires_in=");
        String refreshToken = StringUtils.substringAfter(items[2], "refresh_token=");

        return new AccessGrant(accessToken, null, refreshToken, Long.parseLong(expiresIn));
    }


}
