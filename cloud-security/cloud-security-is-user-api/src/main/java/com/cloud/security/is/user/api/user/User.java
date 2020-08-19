package com.cloud.security.is.user.api.user;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/1/14
 */
@Data
@Entity
public class User {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;

    @Column(unique = true)
    @NotBlank
    private String username;


    @NotBlank
    private String password;

    private String permissions;

    public UserInfo buildUserInfo(){
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(this,userInfo);
        return userInfo;
    }}
