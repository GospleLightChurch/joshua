package org.gyt.web.api.ws;

import org.gyt.web.api.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户web接口
 * Created by Administrator on 2016/9/18.
 */
@RestController
@RequestMapping("/api/role")
public class RoleWebServiceAPI {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public boolean removeRole(
            @RequestParam String username,
            @RequestParam String role
    ) {
        return roleService.removeFromUser(username, role);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public boolean addRole(
            @RequestParam String username,
            @RequestParam String role
    ) {
        return roleService.addToUser(username, role);
    }
}
