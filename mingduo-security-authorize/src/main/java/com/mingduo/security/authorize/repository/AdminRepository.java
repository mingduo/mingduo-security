package com.mingduo.security.authorize.repository;

import com.mingduo.security.authorize.domain.Admin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 
 * @apiNode:
 * @since 2019/11/29
 * @author : weizc 
 */
@Repository
public class AdminRepository {

    @Value("#{@adminDataStore.getAdminDataMap()}")
    private Map<String, Admin> rbacMap;

    public Admin findAdminByUsername(String username){
        return rbacMap.get(username);
    }
}
