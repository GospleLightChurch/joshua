package org.gyt.web.admin;

import org.gyt.web.core.service.FellowshipService;
import org.gyt.web.core.service.RoleService;
import org.gyt.web.core.utils.ModelAndViewUtils;
import org.gyt.web.core.utils.PaginationComponent;
import org.gyt.web.model.User;
import org.gyt.web.repository.repository.RoleRepository;
import org.gyt.web.repository.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

/**
 * 后台页面路由器
 * Created by Administrator on 2016/9/16.
 */
@RestController
@RequestMapping("/admin/user")
public class AdminUserPageController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private FellowshipService fellowshipService;

    @Autowired
    private ModelAndViewUtils modelAndViewUtils;

    @Autowired
    private PaginationComponent paginationComponent;

    @RequestMapping("/normal")
    public ModelAndView getNormal(Pageable pageable) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-user");
        Page<User> userPage = userRepository.findByRole(pageable, roleRepository.findOne("USER"));
        modelAndView.addObject("users", userPage.getContent());
        paginationComponent.addPagination(modelAndView, userPage, "/admin/user/normal");
        return modelAndView;
    }

    @RequestMapping("/member")
    public ModelAndView getMember(Pageable pageable) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-user");
        Page<User> userPage = userRepository.findByRole(pageable, roleRepository.findOne("MEMBER"));
        modelAndView.addObject("users", userPage.getContent());
        paginationComponent.addPagination(modelAndView, userPage, "/admin/user/member");
        return modelAndView;
    }

    @RequestMapping("/admin")
    public ModelAndView getAdmin(Pageable pageable) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-user");
        Page<User> userPage = userRepository.findByRole(pageable, roleRepository.findOne("ADMIN"));
        modelAndView.addObject("users", userPage.getContent());
        paginationComponent.addPagination(modelAndView, userPage, "/admin/user/admin");
        return modelAndView;
    }

    @RequestMapping("/editor")
    public ModelAndView getEditor(Pageable pageable) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-user");
        Page<User> userPage = userRepository.findByRole(pageable, roleRepository.findOne("EDITOR"));
        modelAndView.addObject("users", userPage.getContent());
        paginationComponent.addPagination(modelAndView, userPage, "/admin/user/editor");
        return modelAndView;
    }

    @RequestMapping("/fsadmin")
    public ModelAndView getFSAdmin(Pageable pageable) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-user");
        Page<User> userPage = userRepository.findByRole(pageable, roleRepository.findOne("FS_ADMIN"));
        modelAndView.addObject("users", userPage.getContent());
        paginationComponent.addPagination(modelAndView, userPage, "/admin/user/fsadmin");
        return modelAndView;
    }

    @RequestMapping("/readmin")
    public ModelAndView getREAdmin(Pageable pageable) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-user");
        Page<User> userPage = userRepository.findByRole(pageable, roleRepository.findOne("RE_ADMIN"));
        modelAndView.addObject("users", userPage.getContent());
        paginationComponent.addPagination(modelAndView, userPage, "/admin/user/normal");
        return modelAndView;
    }

    @RequestMapping("/{username}")
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
