package com.mingduo.security.browser.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mingduo.security.core.properties.SecurityProperites;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : weizc
 * @description:
 * @since 2019/11/7
 */
@Slf4j
public abstract class AbstractSessionStrategy {

    @Autowired
    ObjectMapper objectMapper;

    private final String destinationUrl;
    private final SecurityProperites securityProperites;
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private boolean createNewSession = true;

    public AbstractSessionStrategy( SecurityProperites securityProperites) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(securityProperites.getBrowser().getSession().getSessionInvalidUrl()),
                "url must start with '/' or with 'http(s)'");
        this.destinationUrl = securityProperites.getBrowser().getSession().getSessionInvalidUrl();
        this.securityProperites = securityProperites;

    }


    protected void onInvalidSessionDetected(HttpServletRequest request,
                                         HttpServletResponse response) throws IOException {


        log.info("session失效");

        if (createNewSession) {
            request.getSession();
        }

        String sourceUrl = request.getRequestURI();
        String targetUrl;


        if (StringUtils.endsWithIgnoreCase(destinationUrl, ".html")) {

            //如果是登录页面 或者 登出页面 就正常跳转
            if (securityProperites.getBrowser().getSignInPage().equals(sourceUrl)
                    || StringUtils.equals(sourceUrl, securityProperites.getBrowser().getSignOutPage())
            ) {
                targetUrl = sourceUrl;
            } else {
                //跳到失效页面
                targetUrl = destinationUrl;
            }

            log.info("跳转到:" + targetUrl);

            redirectStrategy.sendRedirect(request, response, destinationUrl);
        } else {
            ResponseEntity<String> responseEntity = buildResponseEntiy();

            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(responseEntity));
        }
    }

    protected ResponseEntity<String> buildResponseEntiy() {
        String message = "session失效";
        if (isConcurrency()) {
            message += "," + "，有可能是并发登录导致的";
        }
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

    protected boolean isConcurrency() {
        return false;
    }

    /**
     * Determines whether a new session should be created before redirecting (to avoid
     * possible looping issues where the same session ID is sent with the redirected
     * request). Alternatively, ensure that the configured URL does not pass through the
     * {@code SessionManagementFilter}.
     *
     * @param createNewSession defaults to {@code true}.
     */
    public void setCreateNewSession(boolean createNewSession) {
        this.createNewSession = createNewSession;
    }


}
