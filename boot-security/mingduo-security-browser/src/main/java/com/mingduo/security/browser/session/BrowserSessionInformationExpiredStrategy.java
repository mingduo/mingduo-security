package com.mingduo.security.browser.session;

import com.mingduo.security.core.properties.SecurityProperites;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 默认的session失效处理策略
 * @description:
 * @since 2019/11/7
 * @author : weizc 
 */
public class BrowserSessionInformationExpiredStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {


    public BrowserSessionInformationExpiredStrategy(SecurityProperites securityProperites) {
        super(securityProperites);
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        super.onInvalidSessionDetected(event.getRequest(), event.getResponse());
    }

    @Override
    protected boolean isConcurrency() {
        return true;
    }
}
