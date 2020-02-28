package mingduosecurity.admin.filter;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import mingduosecurity.admin.domain.TokenInfo;
import mingduosecurity.admin.utils.TokenUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author : weizc
 * @apiNode:  基于 cookie实现
 * @since 2020/1/21
 */
@Slf4j
public class CookieTokenFilter extends SessionTokenFilter {


    @Override
    public Object run() throws ZuulException {

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        Cookie accessCookie = WebUtils.getCookie(request, "font_access_token");
        if (accessCookie != null) {
            addZuulRequestHeader(requestContext, accessCookie.getValue());
        } else {
            Cookie refreshCookie = WebUtils.getCookie(request, "font_refresh_token");
            if (refreshCookie != null) {
                try {

                    TokenInfo newTokenInfo = TokenUtils.getRefeshTokenInfo(refreshCookie.getValue());
                    addZuulRequestHeader(requestContext, newTokenInfo.getAccess_token());

                    //set cookie
                    adminProperties.getStoreStrategy()
                            .process(new ServletWebRequest(requestContext.getRequest()), newTokenInfo);


                    return null;

                } catch (Exception e) {
                    log.error("refresh_token 失败", e);

                }
            }
            handleErrorResponse(requestContext);
        }


        return null;
    }
}
