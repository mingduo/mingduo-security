package com.mingduo.security.demo.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mingduo.security.demo.dto.User;
import com.mingduo.security.demo.dto.UserQueryCondition;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : weizc
 * @description:
 * @since 2019/10/14
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @ApiOperation("查询用户信息")
    @GetMapping
    public List<User> query(UserQueryCondition condition, @PageableDefault(page = 1, size = 15, sort = "username,desc") Pageable pageable) {

        System.out.println(ReflectionToStringBuilder
                .toString(condition, ToStringStyle.MULTI_LINE_STYLE));

        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getSort());


        List<User> userList = new ArrayList<>(4);

        userList.add(new User());
        userList.add(new User());
        userList.add(new User());

        return userList;
    }


    @JsonView(User.UserSimpleView.class)
    @GetMapping("/{id:\\d+}")
    public User getInfo(@PathVariable String id) {
        System.out.println("进入getInfo服务");

        User user = new User();
        user.setUsername("tom");
        return user;
    }


    @PostMapping("/create")
    public User create(@RequestBody @Valid User user/*, BindingResult bindingResult*/) {
/*
        if(bindingResult.hasErrors()){
            bindingResult.getFieldErrors().stream()
                    .forEach(error-> System.out.println(error.getDefaultMessage()));
        }
*/

        System.out.println(ReflectionToStringBuilder
                .toString(user, ToStringStyle.MULTI_LINE_STYLE));

        user.setId("1");

        return user;

    }


    @PutMapping("/update/{id:\\d+}")
    public User update(@Valid @RequestBody User user, BindingResult errors) {



        System.out.println(ReflectionToStringBuilder
                .toString(user, ToStringStyle.MULTI_LINE_STYLE));

        user.setId("1");

        return user;
    }



    @DeleteMapping("/delete/{id:\\d+}")
    public void delete(@PathVariable @ApiParam("用户id") String id){
        System.out.println("id = [" + id + "]");
    }
}
