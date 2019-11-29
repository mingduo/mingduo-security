package com.mingduo.security.authorize.repository;

import com.mingduo.security.authorize.domain.Admin;
import com.mingduo.security.core.constants.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : weizc
 * @apiNode:
 * @since 2019/11/29
 */
@Component
public class AdminDataStore {

    @Autowired
    PasswordEncoder passwordEncoder;

    Map<String, Admin> storedAdminMap = new HashMap<>(8);

    public Map<String, Admin> getAdminDataMap() {
        String password = passwordEncoder.encode("123");
        SecurityConstants.ADMIN_ROLE_LIST.forEach(n -> {

            Admin admin = new Admin(n, password,
                    AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN,ROLE_USER"));
            admin.setUrls(Collections.singleton("/**"));
            storedAdminMap.put(n, admin);

        });

        SecurityConstants.USER_ROLE_LIST.forEach(n -> {

            Admin admin = new Admin(n, password,
                    AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
            admin.setUrls(Collections.singleton("/hello"));
            storedAdminMap.put(n, admin);

        });


        return Collections.unmodifiableMap(storedAdminMap);

    }
}
