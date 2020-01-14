package com.cloud.security.is.user.api.user;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/1/14
 */
@Data
public class UserInfo {


    private Long id;

    private String name;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;


    private String permissions;


    public UserInfo buildUserInfo(){
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(this,userInfo);
        return userInfo;
    }
}
