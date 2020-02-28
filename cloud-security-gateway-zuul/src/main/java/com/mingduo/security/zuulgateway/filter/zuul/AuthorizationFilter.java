package com.mingduo.security.zuulgateway.filter.zuul;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mingduo.security.zuulgateway.domain.TokenInfo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/1/19
 */
@Slf4j
//@Component
public class AuthorizationFilter extends ZuulFilter {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        if (isNeedAuth(request)) {
            TokenInfo tokenInfo = (TokenInfo) request.getAttribute("tokenInfo");
            if (tokenInfo != null && tokenInfo.isActive()) {
                //没有权限
                if (!hasPermission(request)) {

                    log.error("403 没有权限 ");

                    handleError(HttpStatus.FORBIDDEN.value(), requestContext);

                }
                requestContext.addZuulRequestHeader("username",tokenInfo.getUser_name());
            } else {
                if (!StringUtils.contains(request.getRequestURI(), "/token")) {
                    //没有认证

                    log.error("401 没有认证 ");
                    handleError(HttpStatus.UNAUTHORIZED.value(), requestContext);
                }
            }

        }


        return null;
    }

    private void handleError(int status, RequestContext requestContext) {
        requestContext.getResponse().setStatus(status);
        ResponseEntity<String> responseEntity = ResponseEntity.status(status).body("auth fail");
        requestContext.setResponseBody(responseEntity.getBody());
        requestContext.setSendZuulResponse(false);
    }

    /**
     * 二分之一的概率
     *
     * @param request
     * @return
     */
    private boolean hasPermission(HttpServletRequest request) {
        if (StringUtils.startsWith(request.getRequestURI(), "/token")) {
            return true;
        }
        return true;
        //return new Random().nextInt() % 2 == 1;
    }

    private boolean isNeedAuth(HttpServletRequest request) {
        return true;
    }
}
