package mingduosecurity.admin.utils;

import mingduosecurity.admin.domain.TokenInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/1/21
 */
public class TokenUtils {

    static RestTemplate restTemplate = new RestTemplate();

    /**
     * authorization_code 获取access_token
     *
     * @param code
     * @return
     */
    public static TokenInfo getTokenInfo(String code) {
        String url = "http://localhost:9090/oauth/token";
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>(4);
        params.add("grant_type", "authorization_code");
        params.add("code", code);
        params.add("redirect_uri", "http://httpbin.org/get");
        params.add("client_id", "font");


        HttpHeaders headers = getHttpHeaders();

        TokenInfo tokenInfo = restTemplate.postForObject(url, new HttpEntity(params, headers), TokenInfo.class);

        return tokenInfo.init();
    }


    /**
     * refresh获取access_token
     *
     * @param refreshToken
     * @return
     */
    public static TokenInfo getRefeshTokenInfo(String refreshToken) {
        String url = "http://localhost:9090/oauth/token";
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>(4);
        params.add("grant_type", "refresh_token");
        params.add("refresh_token", refreshToken);


        HttpHeaders headers = getHttpHeaders();

        TokenInfo tokenInfo = restTemplate.postForObject(url, new HttpEntity(params, headers), TokenInfo.class);

        return tokenInfo.init();
    }

    private static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("font", "123456");
        return headers;
    }


    public static void setAccessTokenCookie(ServletWebRequest webRequest, TokenInfo tokenInfo) {
        HttpServletResponse response = webRequest.getResponse();
        Cookie accessCookie = new Cookie("font_access_token", tokenInfo.getAccess_token());
        accessCookie.setMaxAge(tokenInfo.getExpires_in().intValue());
        accessCookie.setDomain("localhost");
        accessCookie.setPath("/");
        response.addCookie(accessCookie);

    }


    public static void setRefreshTokenCookie(ServletWebRequest webRequest, TokenInfo tokenInfo) {
        HttpServletResponse response = webRequest.getResponse();

        Cookie refreshCookie = new Cookie("font_refresh_token", tokenInfo.getRefresh_token());
        refreshCookie.setMaxAge(2592000);
        refreshCookie.setDomain("localhost");
        refreshCookie.setPath("/");

        response.addCookie(refreshCookie);

    }
}
