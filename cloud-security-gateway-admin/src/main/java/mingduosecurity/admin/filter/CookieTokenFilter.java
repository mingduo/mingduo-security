package mingduosecurity.admin.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;

/**
 * 
 * @apiNode:
 * @since 2020/1/21
 * @author : weizc 
 */
public class CookieTokenFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return null;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        return null;
    }
}
