package com.cloud.security.is.user.api.user;

import java.io.IOException;
import java.util.List;

/**
 * 
 * @apiNode:
 * @since 2020/1/14
 * @author : weizc 
 */
public interface UserService {

    UserInfo create(UserInfo user) throws IOException;

    UserInfo update(UserInfo user);

    void delete(Long id);

    UserInfo get(Long id);

    List<UserInfo> query(String name);

    UserInfo login(UserInfo user);

}
