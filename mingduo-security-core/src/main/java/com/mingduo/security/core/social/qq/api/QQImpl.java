package com.mingduo.security.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * @author : weizc
 * @description:
 * @since 2019/10/30
 */
@Slf4j
@Data
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    /**
     * https://wiki.connect.qq.com/%E6%A0%A1%E9%AA%8Ctoken%E6%98%AF%E5%90%A6%E6%9C%89%E6%95%88
     * <p>
     * 成功返回appid和openid：
     * callback("client_id":"1105192975","openid":"AF4AFE3AE56D12EF1BB88BC6AAC86AEF"});
     * <p>
     * 失败报100016错误
     * callback({"error":100016,"error_description":"access token check failed"});
     */
    public static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    /**
     * 获取用户的基本信息URL，可以查看 QQ互联开放平台 看到，
     * 本来有三个参数，其中一个access_token会交给AbstractOAuth2ApiBinding处理，
     * AbstractOAuth2ApiBinding会把access_token
     * 	 这个参数加进去，所以这里就没有
     */
    public static final String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
    /**
     * 申请QQ登录成功后，分配给应用的appid
     */
    private String appId;
    /**
     * 用户的ID，与QQ号码一一对应。
     * 可通过调用https://graph.qq.com/oauth2.0/me?access_token=YOUR_ACCESS_TOKEN 来获取。
     */
    private String openid;

    private ObjectMapper objectMapper=new ObjectMapper();

    public QQImpl(String accessToken, String appId) {
        //默认TokenStrategy.AUTHORIZATION_HEADER 从请求头获取
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;

//callback("client_id":"1105192975","openid":"AF4AFE3AE56D12EF1BB88BC6AAC86AEF"});
        String getOpenIdUrl = String.format(URL_GET_OPENID, accessToken);
        // 发送get请求获取openId

        String result = this.getRestTemplate().getForObject(getOpenIdUrl, String.class);

        String openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");

        log.info("openid:"+result);

        this.openid = openId;
    }

    @Override
    public QQUserInfo getUserInfo() {
        String getUserInfoUrl = String.format(URL_GET_USER_INFO, this.appId,this.openid);
        // 获取用户信息
        String result = this.getRestTemplate().getForObject(getUserInfoUrl, String.class);

        log.info("qq获取用户信息 result:{}",result);

        QQUserInfo qqUserInfo = null;
        try {
            // 将返回的JSON String读到QQUserInfo对象中
            qqUserInfo = objectMapper.readValue(result, QQUserInfo.class);

            qqUserInfo.setOpenId(this.openid);
        } catch (IOException e) {
            throw new RuntimeException("获取用户信息失败", e);
        }

        return qqUserInfo;
    }
}
