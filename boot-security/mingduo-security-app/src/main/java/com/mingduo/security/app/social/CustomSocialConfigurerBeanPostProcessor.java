package com.mingduo.security.app.social;

import com.mingduo.security.core.constants.SecurityConstants;
import com.mingduo.security.core.social.support.CustomSocialConfigurer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 *
 * 在App初始化完成之后修改掉signup的路径
 * @since 2019/11/20
 * @author : weizc 
 */
@Component
public class CustomSocialConfigurerBeanPostProcessor implements BeanPostProcessor {

    /** (non-Javadoc)
     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
     * 在 SocialSecurityConfig bean实例化之后修改其signUpUrl
     * 由于所有bean在实例化之后都要只需这个方法，所以要判断一下bean的名字
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(StringUtils.equals(beanName, "socialConfigurer")){
           CustomSocialConfigurer socialConfigurer= (CustomSocialConfigurer) bean;
            socialConfigurer.signupUrl(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL);
        }
        return bean;
    }
}
