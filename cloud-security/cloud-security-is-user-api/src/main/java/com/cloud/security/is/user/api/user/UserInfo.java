package com.cloud.security.is.user.api.user;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;

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



    public boolean hasPermission(String method) {

        if (StringUtils.equalsIgnoreCase(method, HttpMethod.GET.name())) {
            if (StringUtils.contains(permissions, "r")) {
                return true;
            }
        } else {
            if (StringUtils.contains(permissions, "w")) {
                return true;
            }
        }
        return false;
    }
}
