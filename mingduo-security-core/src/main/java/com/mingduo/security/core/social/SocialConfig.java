package com.mingduo.security.core.social;

import com.mingduo.security.core.properties.QQProperties;
import com.mingduo.security.core.properties.SecurityProperites;
import com.mingduo.security.core.properties.WeixinProperties;
import com.mingduo.security.core.social.qq.api.QQ;
import com.mingduo.security.core.social.qq.connect.QQConnectionFactory;
import com.mingduo.security.core.social.support.CustomSocialConfigurer;
import com.mingduo.security.core.social.view.WeixinConnectView;
import com.mingduo.security.core.social.weixin.api.Weixin;
import com.mingduo.security.core.social.weixin.connect.WeixinConnectionFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.*;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;
import java.util.List;

/**
 * 社交登录配置主类
 *
 * @author : weizc
 * @description:
 * @since 2019/10/30
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperites securityProperites;

    @Autowired
    private ObjectProvider<List<ConnectionFactory<?>>> qqConnectionFactorys;

    /*@Autowired
    private ObjectProvider<ConnectionFactory<Weixin>> weixinConnectionFactory;*/
    @Autowired(required = false)
    ConnectionSignUp connectionSignUp;


    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        // 第三个参数是对插入到数据库中的数据做加密，这里为了看的清楚，没有做任何处理，即noOpText
        JdbcUsersConnectionRepository connectionRepository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        // 为表名增加前缀
        connectionRepository.setTablePrefix("tbl_");
        //设置 newUserId 实现社交用户自动登录
        // setConnectionSignUp（connectionSignUp()） 会根据 bean-name =connectionSignUp 查找
        connectionRepository.setConnectionSignUp(connectionSignUp);
        return connectionRepository;
    }

    /* 打开后会自动注册
    @ConditionalOnMissingBean(ConnectionSignUp.class)
    @Bean
    public ConnectionSignUp connectionSignUp() {
        return new SocialConnectionSignUp();
    }
*/
    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {

        qqConnectionFactorys.ifAvailable(t->t.forEach(conectFactory ->
                connectionFactoryConfigurer.addConnectionFactory(conectFactory)));
       // qqConnectionFactory.ifAvailable(conectFactory -> connectionFactoryConfigurer.addConnectionFactory(conectFactory));
      //  weixinConnectionFactory.ifAvailable(conectFactory -> connectionFactoryConfigurer.addConnectionFactory(conectFactory));

    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @ConditionalOnProperty(prefix = "my.security.social.qq", name = "app-id")
    @Bean
    public ConnectionFactory<QQ> qqConnectionFactory() {
        QQProperties qqProperties = securityProperites.getSocial().getQq();
        return new QQConnectionFactory(qqProperties.getProviderId(), qqProperties.getAppId(), qqProperties.getAppSecret());
    }

    /**
     * 社交登录配置类，供浏览器或app模块引入设计登录配置用。
     *
     * @return
     */
    @ConditionalOnProperty(prefix = "my.security.social.weixin", name = "app-id")
    @Bean
    public ConnectionFactory<Weixin> weixinConnectionFactory() {
        WeixinProperties weixinProperties = securityProperites.getSocial().getWeixin();
        return new WeixinConnectionFactory(weixinProperties.getProviderId(), weixinProperties.getAppId(), weixinProperties.getAppSecret());
    }


    @Bean
    public SpringSocialConfigurer socialConfigurer() {

        SpringSocialConfigurer socialConfigurer = new CustomSocialConfigurer(securityProperites.getSocial().getFilterProcessesUrl());
        socialConfigurer.signupUrl(securityProperites.getBrowser().getSignUpUrl());
        return socialConfigurer;
    }

    /**
     * 用来处理注册流程的工具类
     *
     * @param locator
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator locator) {
        return new ProviderSignInUtils(locator, getUsersConnectionRepository(locator));
    }

    /**
     * 用于 绑定 和 解绑 的 controller
     *
     * @param locator
     * @return
     */
    @Bean
    public ConnectController connectControllerConnectController(ConnectionFactoryLocator locator, ConnectionRepository connectionRepository) {
        ConnectController connectController = new ConnectController(locator, connectionRepository);
        //connectController.setc
        return connectController;
    }

    /**
     * bean Name view
     * @return
     */
    @Bean(name = {"connect/weixin", "connect/weixinConnected"})
    @ConditionalOnMissingBean(name = "weixinConnectedView")
    public WeixinConnectView weixinConnectedView() {
        return new WeixinConnectView();
    }
}
