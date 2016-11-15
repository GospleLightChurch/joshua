package org.gyt.web.admin;

import org.apache.commons.lang3.StringUtils;
import org.gyt.web.core.service.FellowshipService;
import org.gyt.web.core.service.RoleService;
import org.gyt.web.core.utils.ModelAndViewUtils;
import org.gyt.web.core.utils.PaginationComponent;
import org.gyt.web.model.User;
import org.gyt.web.repository.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * 后台页面路由器
 * Created by Administrator on 2016/9/16.
 */
@RestController
@RequestMapping("/admin")
public class AdminUserPageController {

    @Autowired
    private UserRepository userRepository;

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
            @RequestParam(required = false) String type, Pageable pageable
    ) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-user");

        Page<User> userPage = new PageImpl<>(new ArrayList<>());

        if (StringUtils.isEmpty(type)) {
            modelAndView.addObject("users", new ArrayList<>());
            modelAndView.addObject("subtitle", "未知类型");
        } else if (type.equalsIgnoreCase("ADMIN")) {
            modelAndView.addObject("subtitle", "系统管理员");
            userPage = userRepository.findByRole(pageable, "ADMIN");
        } else if (type.equalsIgnoreCase("EDITOR")) {
            modelAndView.addObject("subtitle", "网站编辑");
            userPage = userRepository.findByRole(pageable, "EDITOR");
        } else if (type.equalsIgnoreCase("FS_ADMIN")) {
            modelAndView.addObject("subtitle", "团契管理员");
            userPage = userRepository.findByRole(pageable, "FS_ADMIN");
        } else if (type.equalsIgnoreCase("RE_ADMIN")) {
            modelAndView.addObject("subtitle", "资源管理员");
            userPage = userRepository.findByRole(pageable, "RE_ADMIN");
        } else if (type.equalsIgnoreCase("MEMBER")) {
            modelAndView.addObject("subtitle", "团契成员");
            userPage = userRepository.findByRole(pageable, "MEMBER");
        } else if (type.equalsIgnoreCase("USER")) {
            modelAndView.addObject("subtitle", "注册用户");
            userPage = userRepository.findByRole(pageable, "USER");
        } else {
            modelAndView.addObject("users", new ArrayList<>());
            modelAndView.addObject("subtitle", "未知类型");
        }

        modelAndView.addObject("users", userPage.getContent());
        paginationComponent.addPagination(modelAndView, userPage, "/admin/user?type=" + type);
        return modelAndView;
    }

    @RequestMapping("/user/{username}")
    public ModelAndView userDetailsPage(
            @PathVariable String username
    ) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-user-details");

        User user = userRepository.findOne(username);

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
