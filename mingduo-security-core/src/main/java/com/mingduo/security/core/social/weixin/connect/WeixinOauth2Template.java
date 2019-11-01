package com.mingduo.security.core.social.weixin.connect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @author : weizc
 * @description:
 * @since 2019/10/31
 */
@Slf4j
@Getter
@Setter
public class WeixinOauth2Template extends OAuth2Template {

    private String clientId;

    private String clientSecret;

    private String accessTokenUrl;

    private String authorizeUrl;


    private ObjectMapper objectMapper = new ObjectMapper();

    public WeixinOauth2Template(String clientId, String clientSecret, String authenticateUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authenticateUrl, accessTokenUrl);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authorizeUrl = authenticateUrl;
        this.accessTokenUrl = accessTokenUrl;
        setUseParametersForClientAuthentication(true);
    }

    /**
     * 第三方使用网站应用授权登录前请注意已获取相应网页授权作用域（scope=snsapi_login）
     * ，则可以通过在PC端打开以下链接：
     * https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect 若提示“该链接无法访问”，
     * 请检查参数是否填写错误，如redirect_uri的域名与审核时填写的授权域名不一致或scope不为snsapi_login。
     *
     * @param parameters
     * @return
     */
    @Override
    public String buildAuthenticateUrl(OAuth2Parameters parameters) {
        String url = super.buildAuthorizeUrl(parameters);
        return String.format("%s&appId=%s&scope=snsapi_login", url, clientId);

    }

    @Override
    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri, MultiValueMap<String, String> additionalParameters) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.set("appid", clientId);
        params.set("secret", clientSecret);
        params.set("code", authorizationCode);
        params.set("redirect_uri", redirectUri);
        params.set("grant_type", "authorization_code");
        if (additionalParameters != null) {
            params.putAll(additionalParameters);
        }
        return postForAccessGrant(accessTokenUrl, params);
    }

    /**
     * contentType=text/plain 不支持
     *
     * @param accessTokenUrl
     * @param parameters
     * @return
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String result = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        log.info("获取access_token, 响应内容: " + result);

        WeixinAccessGrant accessGrant;
        try {
            Map resultMap = objectMapper.readValue(result, Map.class);
            accessGrant=new WeixinAccessGrant(MapUtils.getString(resultMap, "access_token"),
                    MapUtils.getString(resultMap, "scope"),
                    MapUtils.getString(resultMap, "refresh_token"),
                    MapUtils.getLong(resultMap, "expires_in"),
                    MapUtils.getString(resultMap, "openid")
                    );
        } catch (IOException e) {
            throw new RuntimeException("解析失败", e);
        }
        return accessGrant;
    }


    /**
     * 不支持 text/plain这种类型 自己增加一个 HttpMessageConverter
     *
     * @return
     */
    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        messageConverters.add(new StringHttpMessageConverter(Charset.defaultCharset()));
        return restTemplate;
    }
}
