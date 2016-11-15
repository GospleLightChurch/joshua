package org.gyt.web.api.ws;

import org.gyt.web.api.service.FellowshipService;
import org.gyt.web.api.service.RoleService;
import org.gyt.web.model.Fellowship;
import org.gyt.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 团契web接口
 * Created by Administrator on 2016/9/18.
 */
@RestController
@RequestMapping("/api/fellowship")
public class FellowshipWebServiceAPI {

    @Autowired
    private FellowshipService fellowshipService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    public String disable(@RequestParam String name) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Fellowship fellowship = fellowshipService.get(name);

        if (fellowship != null && fellowship.isEnable() && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MANAGE_FELLOWSHIP"))) {
            return fellowshipService.disable(name) ? "success" : "禁用团契失败";
        }

        return "团契不存在或者已经禁用";
    }

    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    public String enable(@RequestParam String name) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Fellowship fellowship = fellowshipService.get(name);

        if (fellowship != null && !fellowship.isEnable() && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MANAGE_FELLOWSHIP"))) {
            return fellowshipService.enable(name) ? "success" : "激活团契失败";
        }

        return "团契不存在或者已经激活";
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public String transfer(@RequestParam String name, String username) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Fellowship fellowship = fellowshipService.get(name);

        if (fellowship != null && fellowship.isEnable()) {
            if (fellowship.getOwner().getUsername().equals(user.getUsername()) || user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MANAGE_FELLOWSHIP"))) {
                if (fellowshipService.setOwner(name, username)) {
                    roleService.addToUser(username, "MEMBER");
                    return "success";
                } else {
                    return "指定的用户不存在";
                }
            } else {
                return "只有拥有团契管理权限或者是本团契所有者才能更改本团契的所有者。";
            }
        }

        return "团契不存在或者已经禁用";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestParam String name, String username) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Fellowship fellowship = fellowshipService.get(name);

        if (fellowship != null && fellowship.isEnable()) {
            if (fellowship.getOwner().getUsername().equals(user.getUsername()) || fellowship.getAdmins().stream().anyMatch(user1 -> user1.getUsername().equals(user.getUsername())) || user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MANAGE_FELLOWSHIP"))) {
                if (fellowshipService.addAdmin(name, username)) {
                    roleService.addToUser(username, "MEMBER");
                    return "success";
                } else {
                    return "指定的用户不存在，或者已经添加该管理员";
                }
            } else {
                return "只有拥有团契管理权限或者是本团契管理员才能添加管理员。";
            }
        }

        return "团契不存在或者已经禁用";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(@RequestParam String name, String username) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Fellowship fellowship = fellowshipService.get(name);

        if (fellowship != null && fellowship.isEnable()) {
            if (fellowship.getOwner().getUsername().equals(user.getUsername()) || user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MANAGE_FELLOWSHIP"))) {
                if (fellowshipService.removeAdmin(name, username)) {
                    return "success";
                } else {
                    return "指定的用户不存在，或者已经移除该管理员";
                }
            } else {
                return "只有拥有团契管理权限或者是本团契所有者才能移除管理员。";
            }
        }

        return "团契不存在或者已经禁用";
    }
}
