package org.gyt.web.api.service.impl;

import org.gyt.web.api.repository.UserRepository;
import org.gyt.web.api.service.UserService;
import org.gyt.web.api.utils.LoginUtils;
import org.gyt.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean create(User user) {
        if (user.getUsername() != null && get(user.getUsername()) == null) {
            if (
                    LoginUtils.isValidUsername(user.getUsername()) &&
                            LoginUtils.isValidPassword(user.getPassword()) &&
                            LoginUtils.isValidNickname(user.getNickname())
                    ) {
                user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public User get(String username) {
        return null != username ? userRepository.findOne(username) : null;
    }

    @Override
    public List<User> get(int pageNumber, int pageSize) {
        return get(pageNumber, pageSize, "ASC", "username");
    }

    @Override
    public List<User> get(int pageNumber, int pageSize, String order, String... sort) {
        Pageable pageable = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.fromString(order), sort);
        return userRepository.findAll(pageable).getContent();
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public void update(User user) {
        if (null != get(user.getUsername())) {
            userRepository.save(user);
        }
    }

    @Override
    public boolean lock(String username) {
        User user = get(username);
        if (null != user) {
            if (user.isAccountNonLocked()) {
                user.setLocked(true);
                update(user);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isLocked(String username) {
        User user = get(username);
        return null != user && !user.isAccountNonLocked();
    }

    @Override
    public boolean unlock(String username) {
        User user = get(username);
        if (null != user) {
            if (!user.isAccountNonLocked()) {
                user.setLocked(false);
                update(user);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean enable(String username) {
        User user = get(username);
        if (null != user) {
            if (!user.isEnabled()) {
                user.setDisabled(false);
                update(user);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isEnable(String username) {
        User user = get(username);
        return null != user && user.isEnabled();
    }

    @Override
    public boolean disable(String username) {
        User user = get(username);
        if (null != user) {
            if (user.isEnabled()) {
                user.setDisabled(true);
                update(user);
                return true;
            }
        }

        return false;
    }
}
