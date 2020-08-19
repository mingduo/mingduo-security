package com.mingduo.security.app.social;

import com.mingduo.security.app.exception.AppSecretException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInAttempt;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author : weizc
 * @description:
 * @since 2019/11/20
 */
@Slf4j
@Component
public class AppSignUpUtils {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    UsersConnectionRepository connectionRepository;
    @Autowired
    ConnectionFactoryLocator connectionFactoryLocator;


    /**
     * 将缓存的社交网站用户信息与系统注册用户信息绑定
     *
     * @param webRequest
     * @param userId
     */
    public void doPostSignUp(String userId, ServletWebRequest webRequest) {
        String key = getDeviceId(webRequest);

        if (!redisTemplate.hasKey(key)) {
            throw new AppSecretException("无法找到缓存的用户社交账号信息");
        }

        ConnectionData connectionData = (ConnectionData) redisTemplate.opsForValue().get(key);

        connectionRepository.createConnectionRepository(userId).addConnection(getConnection(connectionFactoryLocator, connectionData));
        redisTemplate.delete(key);

    }

    /**
     *
     * @param connectionFactoryLocator
     * @param connectionData
     * @return
     *      copy from
     * @see ProviderSignInAttempt
     * @see ProviderSignInUtils
     */
    private Connection<?> getConnection(ConnectionFactoryLocator connectionFactoryLocator, ConnectionData connectionData) {
        return connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId()).createConnection(connectionData);
    }

    /**
     * 缓存社交网站用户信息到redis
     *
     * @param connectionData
     * @param webRequest
     */
    public void saveConnectData(ConnectionData connectionData, ServletWebRequest webRequest) {
        redisTemplate.opsForValue().set(getDeviceId(webRequest), connectionData);
    }


    private String getDeviceId(ServletWebRequest webRequest) {
        String deviceId = webRequest.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            log.error("社交注册无法获取deviceId:{}", deviceId);
            throw new AppSecretException("设备id参数不能为空");
        }
        return "social:register:deviceId:" + deviceId;
    }
}
