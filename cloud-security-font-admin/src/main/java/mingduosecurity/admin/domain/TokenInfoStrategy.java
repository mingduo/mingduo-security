package mingduosecurity.admin.domain;

import mingduosecurity.admin.utils.TokenUtils;
import org.springframework.web.context.request.ServletWebRequest;

public  enum TokenInfoStrategy {
    SESSION{
        @Override
        public void process(ServletWebRequest webRequest, TokenInfo tokenInfo) {
            webRequest.getRequest().getSession().setAttribute("font_token_info",tokenInfo);
        }
    },
    COOKIE {
        @Override
        public void process(ServletWebRequest webRequest, TokenInfo tokenInfo) {
            TokenUtils.setAccessTokenCookie(webRequest,tokenInfo);

            TokenUtils.setRefreshTokenCookie(webRequest,tokenInfo);

        }
    };

   public abstract void process(ServletWebRequest webRequest,TokenInfo tokenInfo);

}
