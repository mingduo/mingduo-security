package mingduosecurity.admin.domain;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 
 * @apiNode:
 * @since 2020/1/21
 * @author : weizc 
 */
public interface TokenInfoSaveStrategy {

    void process(ServletWebRequest webRequest);
}
