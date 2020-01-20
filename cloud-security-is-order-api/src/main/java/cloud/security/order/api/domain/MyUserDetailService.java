package cloud.security.order.api.domain;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 
 * @apiNode:
 * @since 2020/1/19
 * @author : weizc 
 */

public class MyUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = new User(username,"123", AuthorityUtils.createAuthorityList("ROLE_ADMIN"));

        return user;
    }
}
