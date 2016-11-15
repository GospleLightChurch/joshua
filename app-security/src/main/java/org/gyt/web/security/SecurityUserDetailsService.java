package org.gyt.web.security;

import org.gyt.web.api.service.UserService;
import org.gyt.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by y27chen on 2016/7/15.
 */
@Service("userDetailsService")
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.get(username.toLowerCase());
        if (user == null || user.isDisabled()) {
            throw new UsernameNotFoundException(String.format("can't found user: %s", username));
        }

        return user;
    }
}
