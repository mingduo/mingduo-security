package com.mingduo.security.demo.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mingduo.security.demo.dto.User;
import com.mingduo.security.demo.dto.UserQueryCondition;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : weizc
 * @description:
 * @since 2019/10/14
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    ProviderSignInUtils providerSignInUtils;

    @PostMapping("/register")
    public void register(User user, HttpServletRequest request){
        //不管是注册用户还是绑定用户，都会拿到一个用户唯一标识。
        providerSignInUtils.doPostSignUp(user.getUsername(), new ServletWebRequest(request));
    }

    @GetMapping("/me")
    public Object getCurrentUser(/*@AuthenticationPrincipal UserDetails*/ Authentication user){
        log.info("authentication:{}",ReflectionToStringBuilder.toString(user,ToStringStyle.MULTI_LINE_STYLE));
        //user= SecurityContextHolder.getContext().getAuthentication();
        return user;
    }


    @ApiOperation("查询用户信息")
    @GetMapping
    public List<User> query(UserQueryCondition condition, @PageableDefault(page = 1, size = 15, sort = "username,desc") Pageable pageable) {

        log.info(ReflectionToStringBuilder
                .toString(condition, ToStringStyle.MULTI_LINE_STYLE));

        log.info(""+pageable.getPageNumber());
        log.info(""+pageable.getPageSize());
        log.info(""+pageable.getSort());


        List<User> userList = new ArrayList<>(4);

        userList.add(new User());
        userList.add(new User());
        userList.add(new User());

        return userList;
    }


    @JsonView(User.UserSimpleView.class)
    @GetMapping("/{id:\\d+}")
    public User getInfo(@PathVariable String id) {
        log.info("进入getInfo服务");

        User user = new User();
        user.setUsername("tom");
        return user;
    }


    @PostMapping
    public User create(@RequestBody @Valid User user/*, BindingResult bindingResult*/) {
/*
        if(bindingResult.hasErrors()){
            bindingResult.getFieldErrors().stream()
                    .forEach(error-> log.info(error.getDefaultMessage()));
        }
*/

        log.info(ReflectionToStringBuilder
                .toString(user, ToStringStyle.MULTI_LINE_STYLE));

        user.setId("1");

        return user;

    }


    @PutMapping("/{id:\\d+}")
    public User update(@Valid @RequestBody User user, BindingResult errors) {



        log.info(ReflectionToStringBuilder
                .toString(user, ToStringStyle.MULTI_LINE_STYLE));

        user.setId("1");

        return user;
    }



    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable @ApiParam("用户id") String id){
        log.info("id = [" + id + "]");
    }
}
