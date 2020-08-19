package com.mingduo.security.app.authentication.openid;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Set;

/**
 * @author :    weizc
 * @since 2019/11/16
 */
@NoArgsConstructor
@AllArgsConstructor
public class OpenIdAuthenticationProvider implements AuthenticationProvider {
    UserDetailsService userDetailsService;

    UsersConnectionRepository usersConnectionRepository;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OpenIdAuthenticationToken authToken = ((OpenIdAuthenticationToken) authentication);

        String providerId = authToken.getProviderId();
        Set<String> providerUserIds = Collections.singleton(String.valueOf(authToken.getPrincipal()));

        Set<String> userIds = usersConnectionRepository.findUserIdsConnectedTo(providerId, providerUserIds);
        if (CollectionUtils.isEmpty(userIds) || userIds.size() != 1) {

            throw new InternalAuthenticationServiceException("无法获取用户信息");

        }

        String userId = userIds.iterator().next();

        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        if (userDetails == null) {
            throw new UsernameNotFoundException("Unknown connected account id");
        }


        return new OpenIdAuthenticationToken(authToken.getPrincipal(), providerId, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OpenIdAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
