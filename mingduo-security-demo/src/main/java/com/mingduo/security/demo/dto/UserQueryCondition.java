package com.mingduo.security.demo.dto;

import lombok.Data;

/**
 * 
 * @description:
 * @since 2019/10/15
 * @author : weizc 
 */
@Data
public class UserQueryCondition {

    private String username;

    private int age;

    private int ageTo;
}
