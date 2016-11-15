package org.gyt.web.admin;

import org.apache.commons.lang3.StringUtils;
import org.gyt.web.api.service.FellowshipService;
import org.gyt.web.api.service.RoleService;
import org.gyt.web.api.service.UserService;
import org.gyt.web.api.utils.ModelAndViewUtils;
import org.gyt.web.api.utils.PaginationComponent;
import org.gyt.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台页面路由器
 * Created by Administrator on 2016/9/16.
 */
@RestController
@RequestMapping("/admin")
public class AdminUserPageController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private FellowshipService fellowshipService;

    @Autowired
    private ModelAndViewUtils modelAndViewUtils;

    @Autowired
    private PaginationComponent paginationComponent;

    @RequestMapping("/user")
    public ModelAndView userTablePage(
            @RequestParam(required = false) String type,
            @RequestParam(required = false, defaultValue = "1") int pageNumber,
            @RequestParam(required = false, defaultValue = "20") int pageSize
    ) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-user");

        List<User> userList = new ArrayList<>();

        if (StringUtils.isEmpty(type)) {
            modelAndView.addObject("users", new ArrayList<>());
            modelAndView.addObject("subtitle", "未知类型");
        } else if (type.equalsIgnoreCase("ADMIN")) {
            userList = userService.getAll().stream().filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"))).collect(Collectors.toList());
            modelAndView.addObject("subtitle", "系统管理员");
        } else if (type.equalsIgnoreCase("EDITOR")) {
            userList = userService.getAll().stream().filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("EDITOR"))).collect(Collectors.toList());
            modelAndView.addObject("subtitle", "网站编辑");
        } else if (type.equalsIgnoreCase("FS_ADMIN")) {
            userList = userService.getAll().stream().filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("FS_ADMIN"))).collect(Collectors.toList());
            modelAndView.addObject("subtitle", "团契管理员");
        } else if (type.equalsIgnoreCase("RE_ADMIN")) {
            userList = userService.getAll().stream().filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("RE_ADMIN"))).collect(Collectors.toList());
            modelAndView.addObject("subtitle", "资源管理员");
        } else if (type.equalsIgnoreCase("MEMBER")) {
            userList = userService.getAll().stream().filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("MEMBER"))).collect(Collectors.toList());
            modelAndView.addObject("subtitle", "团契成员");
        } else if (type.equalsIgnoreCase("USER")) {
            userList = userService.getAll().stream().filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("USER"))).collect(Collectors.toList());
            modelAndView.addObject("subtitle", "注册用户");
        } else {
            modelAndView.addObject("users", new ArrayList<>());
            modelAndView.addObject("subtitle", "未知类型");
        }

        modelAndView.addObject("users", paginationComponent.listPagination(userList, pageNumber, pageSize));
        paginationComponent.addPaginationModel(modelAndView, "/admin/user?type=" + type, userList.size(), pageNumber, pageSize);
        return modelAndView;
    }

    @RequestMapping("/user/{username}")
    public ModelAndView userDetailsPage(
            @PathVariable String username
    ) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-user-details");

        User user = userService.get(username);

        if (null == user) {
            modelAndView.setViewName("404");
            modelAndView.addObject("message", String.format("找不到用户：%s", username));
        } else {
            modelAndView.addObject("user", user);
            modelAndView.addObject("roles", roleService.get().stream().filter(role -> !user.getRoles().stream().anyMatch(role1 -> role1.equals(role))).collect(Collectors.toList()));
            modelAndView.addObject("owners", fellowshipService.getUserOwnerFellowship(username));
            modelAndView.addObject("admins", fellowshipService.getUserAdminFellowship(username));
        }

        return modelAndView;
    }
}
