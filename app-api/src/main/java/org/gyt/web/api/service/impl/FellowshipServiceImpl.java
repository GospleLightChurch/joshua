package org.gyt.web.api.service.impl;

import org.gyt.web.api.repository.FellowshipRepository;
import org.gyt.web.api.service.FellowshipService;
import org.gyt.web.api.service.UserService;
import org.gyt.web.model.Fellowship;
import org.gyt.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FellowshipServiceImpl implements FellowshipService {

    @Autowired
    private FellowshipRepository fellowshipRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<Fellowship> getAll() {
        return fellowshipRepository.findAll();
    }

    @Override
    public Fellowship get(String name) {
        return null != name ? fellowshipRepository.findOne(name) : null;
    }

    @Override
    public boolean create(String name, String displayName) {
        if (null == get(name)) {
            Fellowship fellowship = new Fellowship();
            fellowship.setName(name);
            fellowship.setDisplayName(displayName);
            fellowship.setCreatedDate(new Date());

            fellowshipRepository.save(fellowship);
        }
        return false;
    }

    @Override
    public boolean enable(String name) {
        Fellowship fellowship = get(name);
        if (null != fellowship && !fellowship.isEnable()) {
            fellowship.setEnable(true);
            fellowshipRepository.save(fellowship);
            return true;
        }
        return false;
    }

    @Override
    public boolean isEnabled(String name) {
        Fellowship fellowship = get(name);
        if (null != fellowship) {
            return fellowship.isEnable();
        }
        return false;
    }

    @Override
    public boolean disable(String name) {
        Fellowship fellowship = get(name);
        if (null != fellowship && fellowship.isEnable()) {
            fellowship.setEnable(false);
            fellowshipRepository.save(fellowship);
            return true;
        }
        return false;
    }

    @Override
    public boolean setOwner(String name, String username) {
        Fellowship fellowship = get(name);
        User user = userService.get(username);

        if (null != fellowship && user != null) {
            fellowship.setOwner(user);
            fellowshipRepository.save(fellowship);
            return true;
        }

        return false;
    }

    @Override
    public boolean addAdmin(String name, String username) {
        Fellowship fellowship = get(name);
        User user = userService.get(username);

        if (null != fellowship && user != null) {
            if (!fellowship.getAdmins().stream().anyMatch(u -> u.getUsername().equals(username))) {
                fellowship.getAdmins().add(user);
                fellowshipRepository.save(fellowship);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean removeAdmin(String name, String username) {
        Fellowship fellowship = get(name);
        User user = userService.get(username);

        if (null != fellowship && user != null) {
            for (User u : fellowship.getAdmins()) {
                if (u.getUsername().equals(username)) {
                    fellowship.getAdmins().remove(user);
                    fellowshipRepository.save(fellowship);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public List<Fellowship> getUserOwnerFellowship(String username) {
        return getAll().stream().filter(fellowship -> fellowship.getOwner().getUsername().equals(username)).collect(Collectors.toList());
    }

    @Override
    public List<Fellowship> getUserAdminFellowship(String username) {
        return getAll().stream().filter(fellowship -> fellowship.getAdmins().stream().anyMatch(user -> user.getUsername().equals(username))).collect(Collectors.toList());
    }
}
