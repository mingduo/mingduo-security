package cloud.security.server.auth.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/1/16
 */
@Component
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String rawPassword = String.valueOf(123456);
        String password = passwordEncoder.encode(rawPassword);
        String roleStr = "ROLE_ADMIN";
        if(username.length()>5){
            roleStr="ROLE_USER";
        }
        return User.withUsername(username)
                .password(password)
                .authorities(roleStr)
                .build();
    }
}
