package com.cloud.security.is.user.api.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * 
 * @apiNode:
 * @since 2020/1/14
 * @author : weizc 
 */
public interface UserRepository extends CrudRepository<User,Long> {

    Optional<User> findByUsername(String username);
}
