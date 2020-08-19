package mingduosecurity.admin.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import mingduosecurity.admin.domain.TokenInfo;
import mingduosecurity.admin.properties.AdminProperties;
import mingduosecurity.admin.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpSession;

/**
 * @author : weizc
 * @apiNode: 基于session实现
 * @since 2020/1/21
 */
@Slf4j
public class SessionTokenFilter extends ZuulFilter {

    @Autowired
    AdminProperties adminProperties;

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
        HttpSession session = requestContext.getRequest().getSession();
        TokenInfo tokenInfo = (TokenInfo) session.getAttribute("font_token_info");
        if (tokenInfo != null) {
            if (tokenInfo.isExpired()) {
                try {
                    //refresh_token
                    log.info("token 失效 refresh_token");
                    tokenInfo = TokenUtils.getRefeshTokenInfo(tokenInfo.getRefresh_token());

                    adminProperties.getStoreStrategy().process(new ServletWebRequest(requestContext.getRequest()), tokenInfo);
                } catch (Exception e) {
                    log.error("refresh_token 失败", e);
                    handleErrorResponse(requestContext);
                }

            }
            addZuulRequestHeader(requestContext, tokenInfo.getAccess_token());
        }

        return null;
    }

    protected void addZuulRequestHeader(RequestContext requestContext, String accessToken) {
        requestContext.addZuulRequestHeader("appId","font");
        requestContext.addZuulRequestHeader("Authorization","bearer "+accessToken);
    }


    /**
     * 错误处理
     * @param requestContext
     */
    protected void handleErrorResponse(RequestContext requestContext) {
        requestContext.setResponseBody("{\"message\":\"refresh fail\"}");
        requestContext.setResponseStatusCode(500);
        requestContext.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        requestContext.setSendZuulResponse(false);
    }
}
