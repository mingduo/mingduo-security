package com.mingduo.security.browser.session;

import com.mingduo.security.core.properties.SecurityProperites;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 默认的session失效处理策略
 *
 * @author : weizc
 * @description:
 * @since 2019/11/7
 */
@Slf4j
public class BrowserInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {


    public BrowserInvalidSessionStrategy(SecurityProperites securityProperites) {
        super(securityProperites);
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException {
        super.onInvalidSessionDetected(request, response);
    }
}


