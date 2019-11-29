package com.mingduo.security.demo.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mingduo.security.demo.dto.User;
import com.mingduo.security.demo.dto.UserQueryCondition;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : weizc
 * @description:
 * @since 2019/10/14
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController implements ApplicationContextAware {


    @Autowired
    ProviderSignInUtils providerSignInUtils;

    private ApplicationContext applicationContext;


    @PostMapping("/register")
    public void register(User user, HttpServletRequest request) {
        //不管是注册用户还是绑定用户，都会拿到一个用户唯一标识。
        if (applicationContext.containsBean("appSignUpUtils")) {
            //社交登录注册
            Object appSignUpUtils = applicationContext.getBean("appSignUpUtils");
            Method doPostSignUp = ReflectionUtils.findMethod(appSignUpUtils.getClass(), "appSignUpUtils");
            ReflectionUtils.invokeMethod(doPostSignUp, appSignUpUtils, new Object[]{user.getUsername(), new ServletWebRequest(request)});

        } else {

            providerSignInUtils.doPostSignUp(user.getUsername(), new ServletWebRequest(request));

        }
    }

    /**
     * @param user
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @see org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter#enhance(org.springframework.security.oauth2.common.OAuth2AccessToken, org.springframework.security.oauth2.provider.OAuth2Authentication)
     */
    @GetMapping("/me")
    public Object getCurrentUser(/*@AuthenticationPrincipal UserDetails*/ Authentication user, HttpServletRequest request) throws UnsupportedEncodingException {
        log.info("authentication:{}", ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
        //user= SecurityContextHolder.getContext().getAuthentication();

        //app jwt 登录
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(authorization)) {
            // 获取JWT
            String token = StringUtils.substringAfter(authorization, "bearer").trim();

            String claims = JwtHelper.decode(token).getClaims();

            Map<String, Object> jwt = JsonParserFactory.create().parseMap(claims);
            log.info("jwt claims:{}", jwt);
            // 获取为JWT扩展的信息
            String author = (String) jwt.get("author");

            System.out.println(author);
        }
        return user;
    }


    @ApiOperation("查询用户信息")
    @GetMapping
    public List<User> query(UserQueryCondition condition, @PageableDefault(page = 1, size = 15, sort = "username,desc") Pageable pageable) {

        log.info(ReflectionToStringBuilder
                .toString(condition, ToStringStyle.MULTI_LINE_STYLE));

        log.info("" + pageable.getPageNumber());
        log.info("" + pageable.getPageSize());
        log.info("" + pageable.getSort());


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
    public void delete(@PathVariable @ApiParam("用户id") String id) {
        log.info("id = [" + id + "]");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
