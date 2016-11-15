package org.gyt.web.api.service.impl;

import org.gyt.web.api.repository.RoleRepository;
import org.gyt.web.api.service.RoleService;
import org.gyt.web.api.service.UserService;
import org.gyt.web.model.Authority;
import org.gyt.web.model.Role;
import org.gyt.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<Role> get() {
        return roleRepository.findAll().stream().filter(role -> !role.getName().equals("ROOT")).collect(Collectors.toList());
    }

    @Override
    public Role get(String name) {
        return roleRepository.findOne(name);
    }

    @Override
    public boolean create(String name) {
        return create(name, name);
    }

    @Override
    public boolean create(String name, String displayName) {
        if (!StringUtils.isEmpty(name) && null == get(name)) {
            Role role = new Role();
            role.setName(name);
            role.setDisplayName(displayName);
            roleRepository.save(role);
        }
        return false;
    }

    @Override
    public boolean add(String name, Authority authority) {

        Role role = get(name);

        if (null != role) {
            for (String s : role.getAuthorities()) {
                Authority auth = Authority.valueOf(s);
                if (auth.equals(authority)) {
                    return false;
                }
            }

            role.getAuthorities().add(authority.name());
            roleRepository.save(role);
            return true;
        }

        return false;
    }

    @Override
    public boolean remove(String name, Authority authority) {

        Role role = get(name);

        if (null != role) {
            for (String s : role.getAuthorities()) {
                Authority auth = Authority.valueOf(s);
                if (auth.equals(authority)) {
                    role.getAuthorities().remove(s);
                    roleRepository.save(role);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean enable(String name) {
        Role role = get(name);

        if (null != role && !role.isEnable()) {
            role.setEnable(true);
            roleRepository.save(role);
            return true;
        }

        return false;
    }

    @Override
    public boolean isEnable(String name) {
        Role role = get(name);

        return null != role && role.isEnable();
    }

    @Override
    public boolean disable(String name) {
        Role role = get(name);

        if (null != role && role.isEnable()) {
            role.setEnable(false);
            roleRepository.save(role);
            return true;
        }

        return false;
    }

    @Override
    public Set<Role> getFromUser(String username) {
        return userService.get(username).getRoles();
    }

    @Override
    public boolean addToUser(String username, String roleName) {
        User user = userService.get(username);

        if (null != user) {
            for (Role role : user.getRoles()) {
                if (role.getName().equals(roleName)) {
                    return false;
                }
            }

            user.getRoles().add(get(roleName));
            userService.update(user);
            return true;
        }

        return false;
    }

    @Override
    public boolean removeFromUser(String username, String roleName) {
        User user = userService.get(username);

        if (null != user) {
            for (Role role : user.getRoles()) {
                if (role.getName().equals(roleName)) {
                    user.getRoles().remove(get(roleName));
                    userService.update(user);
                    return true;
                }
            }
        }

        return false;
    }
}
