package com.mingduo.security.core.social.weixin.connect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
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
 * 完成微信的OAuth2认证流程的模板类。国内厂商实现的OAuth2每个都不同,
 * spring默认提供的OAuth2Template适应不了，只能针对每个厂商自己微调。
 * <p>
 * https://developers.weixin.qq.com/doc/oplatform/Website_App/WeChat_Login/Wechat_Login.html
 *
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

    private final String REFRESH_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";

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
     * <p>
     * 构建获取授权码的请求。也就是引导用户跳转到微信的地址。
     *
     * @param parameters
     * @return
     */
    @Override
    public String buildAuthenticateUrl(OAuth2Parameters parameters) {
        String url = super.buildAuthorizeUrl(parameters);
        return String.format("%s&appId=%s&scope=snsapi_login", url, clientId);

    }

    /**
     * 获取 access_token
     *
     * @param authorizationCode
     * @param redirectUri
     * @param additionalParameters
     * @return
     */
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
     * https://api.weixin.qq.com/sns/oauth2/refresh_token
     * ?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
     *
     * @param refreshToken
     * @param additionalParameters
     * @return
     */
    @Override
    public AccessGrant refreshAccess(String refreshToken, MultiValueMap<String, String> additionalParameters) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("appid", clientId);
        params.set("refresh_token", refreshToken);
        params.set("grant_type", "refresh_token");

        if (additionalParameters != null) {
            params.putAll(additionalParameters);
        }
        return postForAccessGrant(REFRESH_URL, params);
    }


    /**
     * contentType=text/plain 不支持
     *{
     * "access_token":"ACCESS_TOKEN",
     * "expires_in":7200,
     * "refresh_token":"REFRESH_TOKEN",
     * "openid":"OPENID",
     * "scope":"SCOPE"
     * }
     * @param accessTokenUrl
     * @param parameters
     * @return
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        log.info("获取access_token, 请求URL: " + accessTokenUrl);

        String result = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        log.info("获取access_token, 响应内容: " + result);

        WeixinAccessGrant accessGrant;
        Map resultMap;
        try {
            resultMap = objectMapper.readValue(result, Map.class);

        } catch (IOException e) {
            throw new RuntimeException("解析失败", e);
        }

        //返回错误码时直接返回空
        if (StringUtils.isNotBlank(MapUtils.getString(resultMap, "errcode"))) {
            String errcode = MapUtils.getString(resultMap, "errcode");
            String errmsg = MapUtils.getString(resultMap, "errmsg");
            throw new RuntimeException("获取access token失败, errcode:" + errcode + ", errmsg:" + errmsg);
        }

        accessGrant = new WeixinAccessGrant(MapUtils.getString(resultMap, "access_token"),
                MapUtils.getString(resultMap, "scope"),
                MapUtils.getString(resultMap, "refresh_token"),
                MapUtils.getLong(resultMap, "expires_in"),
                MapUtils.getString(resultMap, "openid")
        );

        return accessGrant;
    }


    /**
     * 不支持 text/plain这种类型 自己增加一个 HttpMessageConverter
     * 微信返回的contentType是html/text，添加相应的HttpMessageConverter来处理。
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
