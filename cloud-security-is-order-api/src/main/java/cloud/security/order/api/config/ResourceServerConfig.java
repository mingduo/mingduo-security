package cloud.security.order.api.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 
 * @apiNode:
 * @since 2020/1/16
 * @author : weizc 
 */
@EnableGlobalMethodSecurity(prePostEnabled=true)//激活方法级别的权限认证
@EnableResourceServer
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("order-server");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //与acutuaor 整合
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .antMatchers(HttpMethod.POST).access("#oauth2.hasAnyScope('write','all')")
                .antMatchers(HttpMethod.GET).access("#oauth2.hasAnyScope('read','all')")
              //  .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .anyRequest().authenticated();
    }
}
