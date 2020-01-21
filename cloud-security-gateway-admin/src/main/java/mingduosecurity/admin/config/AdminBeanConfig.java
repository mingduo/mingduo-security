package mingduosecurity.admin.config;

import mingduosecurity.admin.filter.CookieTokenFilter;
import mingduosecurity.admin.filter.SessionTokenFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @apiNode:
 * @since 2020/1/21
 * @author : weizc 
 */
@Configuration
public class AdminBeanConfig {

    @ConditionalOnProperty(prefix = "font.admin",name = "store-strategy" ,havingValue ="session",matchIfMissing = true)
    @Bean
    public SessionTokenFilter sessionTokenFilter(){

        return new SessionTokenFilter();
    }


    @ConditionalOnProperty(prefix = "font.admin",name = "store-strategy" ,havingValue ="cookie")
    @Bean
    public CookieTokenFilter cookieTokenFilter(){
        return new CookieTokenFilter();
    }
}
