package cloud.security.server.auth.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.sql.DataSource;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/1/16
 */
@Configuration
@EnableAuthorizationServer
public class Oauth2AuthServerConfig implements AuthorizationServerConfigurer {

    @Autowired
    private DataSource dataSource;
    @Autowired
    AuthenticationConfiguration authenticationConfiguration;
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtAccessTokenConverter accessTokenConverter;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("isAuthenticated()")
                //设置token key 获取  策略
        .tokenKeyAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationConfiguration.getAuthenticationManager())
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter)
                  .tokenEnhancer(accessTokenConverter)
        ;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        //keytool -genkeypair -alias mingduo  -keyalg RSA  -keystore ./my.key
      //  my.key 密码mingduo
        KeyStoreKeyFactory storeKeyFactory=new KeyStoreKeyFactory(new ClassPathResource("my.key"),"mingduo".toCharArray());
        accessTokenConverter.setKeyPair(storeKeyFactory.getKeyPair("mingduo"));
        return accessTokenConverter;
    }
}
