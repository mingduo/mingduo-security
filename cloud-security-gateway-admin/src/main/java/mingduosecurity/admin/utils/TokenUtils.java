package mingduosecurity.admin.utils;

import mingduosecurity.admin.domain.TokenInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @apiNode:
 * @since 2020/1/21
 * @author : weizc 
 */
public class TokenUtils {

    static RestTemplate restTemplate=new RestTemplate();

    /**
     *  authorization_code 获取access_token
     * @param code
     * @return
     */
    public static TokenInfo getTokenInfo(String code) {
        String url="http://localhost:9090/oauth/token";
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>(4);
        params.add("grant_type","authorization_code");
        params.add("code",code);
        params.add("redirect_uri","http://httpbin.org/get");
        params.add("client_id","font");


        HttpHeaders headers = getHttpHeaders();

        TokenInfo tokenInfo = restTemplate.postForObject(url, new HttpEntity(params,headers), TokenInfo.class);

        return tokenInfo.init();
    }


    /**
     *   refresh获取access_token
     * @param refreshToken
     * @return
     */
    public static TokenInfo getRefeshTokenInfo(String refreshToken) {
        String url="http://localhost:9090/oauth/token";
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>(4);
        params.add("grant_type","refresh_token");
        params.add("refresh_token",refreshToken);


        HttpHeaders headers = getHttpHeaders();

        TokenInfo tokenInfo = restTemplate.postForObject(url, new HttpEntity(params,headers), TokenInfo.class);

        return tokenInfo.init();
    }

    private static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("font", "123456");
        return headers;
    }
}
