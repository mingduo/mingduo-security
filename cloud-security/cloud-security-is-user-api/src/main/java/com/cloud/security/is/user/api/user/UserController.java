package com.cloud.security.is.user.api.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 
 * @apiNode:
 * @since 2020/1/14
 * @author : weizc 
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/login")
    public void login(@Validated UserInfo info,HttpServletRequest request)
    {

        UserInfo userInfo = userService.login(info);
        HttpSession session = request.getSession();
        //说明已经有人登录过 防止session固定攻击
        if(session!=null){
            session.invalidate();
        }
        request.getSession(true).setAttribute("user", userInfo);
    }

    @GetMapping("/logout")
    public  void logout(HttpServletRequest request){
        request.getSession().invalidate();
    }

    @PostMapping
    public UserInfo create(@RequestBody @Validated UserInfo user) throws IOException {
        return userService.create(user);
    }

    @PutMapping("/{id}")
    public UserInfo update(@RequestBody UserInfo user) {
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}")
    public UserInfo get(@PathVariable Long id){
        return userService.get(id);
    }

    @GetMapping
    public List<UserInfo> query(String name) {
        log.info("执行用户查询 param: {}={}","name",name);

        return userService.query(name);
    }

}
