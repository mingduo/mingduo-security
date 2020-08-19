package com.cloud.security.is.user.api.config;

import com.cloud.security.is.user.api.user.UserInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * 
 * @apiNode:
 * @since 2020/1/15
 * @author : weizc 
 */
@Configuration
public class JpaConfig {


    @Bean
    public AuditorAware<String> auditorAware(){
       return () ->{
           ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
           HttpSession session = requestAttributes.getRequest().getSession();
           UserInfo user = (UserInfo) session.getAttribute("user");
          String username=null;
           if(user!=null){
               username=user.getUsername();

           }
           return Optional.ofNullable(username);
       };
    }
}