package com.mingduo.security.zuulgateway.filter.zuul;

import com.mingduo.security.zuulgateway.domain.TokenInfo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @apiNode: 检验 access_token
 * @see  org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter
 * @since 2020/1/19
 * @author : weizc 
 */
@Slf4j
//@Component
public class Oauth2Filter extends ZuulFilter {


    private RestTemplate restTemplate=new RestTemplate();
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();


        try {
            TokenInfo tokenInfo = getTokenInfo(request);
            request.setAttribute("tokenInfo", tokenInfo);
        }catch (Exception e){
            log.error("check token failed",e);
        }

        return null;
    }

    private TokenInfo getTokenInfo(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String appId = request.getHeader("appId");
        if(StringUtils.isBlank(header)||!StringUtils.startsWith(header,"bearer")){
            throw new RuntimeException("未发现请求头[Authorization]或者请求没有以[bearer]开始");
        }

        if(StringUtils.isBlank(appId)){
            throw new RuntimeException("未发现请求头[appId]");
        }

        String token = StringUtils.substringAfter(header, "bearer ");

        MultiValueMap<String,Object> params=new LinkedMultiValueMap<String,Object>() {
            {
                add("token",token);
            }
        };

        HttpHeaders httpheaders=new HttpHeaders();
        //设置basic头
        httpheaders.setBasicAuth(appId,"123456");
        httpheaders.set(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        HttpEntity entity = new HttpEntity(params,httpheaders);
        return restTemplate.postForObject("http://localhost:9090/oauth/check_token",
                entity, TokenInfo.class);

    }
}
