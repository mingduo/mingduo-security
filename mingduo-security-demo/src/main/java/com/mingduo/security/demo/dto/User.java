package com.mingduo.security.demo.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.mingduo.security.demo.validator.MyContraint;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * 
 * @description:
 * @since 2019/10/15
 * @author : weizc 
 */
@Data
public class User {


    public interface UserSimpleView{};

    public interface UserDetailView extends UserSimpleView{};


    @JsonView(User.UserSimpleView.class)
    private String id;

    @MyContraint(message = "这是一个测试")
    @JsonView(UserSimpleView.class)
    private String username;

    @NotBlank(message = "密码不能为空")
    @JsonView(UserDetailView.class)
    private String password;

    @Past(message = "生日是过去的时间")
    private Date birthday;
}
