package com.cloud.security.is.user.api.user;

import com.lambdaworks.crypto.SCryptUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/1/14
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserRepository userRepository;

    @Override
    public UserInfo create(UserInfo userInfo) throws IOException {
        User user = new User();
        BeanUtils.copyProperties(userInfo, user);

        user.setPassword(SCryptUtil.scrypt(user.getPassword(), 32768, 8, 1));
        userRepository.save(user);
        userInfo.setId(user.getId());
        return userInfo;
    }

    @Override
    public UserInfo update(UserInfo user) {
        return null;
    }

    @Override
    public void delete(Long id) {
        User user = new User();
        user.setId(id);
        userRepository.delete(user);
    }

    @Override
    public UserInfo get(Long id)  {
        return userRepository.findById(id).get().buildUserInfo();
    }

    @Override
    public List<UserInfo> query(String name) {
        return userRepository.findByUsername(name).map(User::buildUserInfo)
                .map(Collections::singletonList).orElse(Collections.emptyList());
    }

    @Override
    public UserInfo login(UserInfo info) {
        Optional<User> user = userRepository.findByUsername(info.getUsername());
        if(user.isPresent()&&SCryptUtil.check(info.getUsername(), user.get().getPassword())){
            return user.get().buildUserInfo();
        }
        return null;
    }
}
