package org.gyt.web.controller;

import org.gyt.web.api.service.RoleService;
import org.gyt.web.api.service.UserService;
import org.gyt.web.api.utils.ModelAndViewUtils;
import org.gyt.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登录注销注册页面路由器
 * Created by y27chen on 2016/7/12.
 */
@RestController
public class LoginPageController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ModelAndViewUtils modelAndViewUtils;

    @RequestMapping("/login")
    public ModelAndView getPage(
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String logout
    ) {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("login");
        modelAndView.addObject("user", new User());

        if (null != error) {
            modelAndView.addObject("login", "failed");
        }

        if (null != logout) {
            modelAndView.addObject("logout", "success");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/forget", method = RequestMethod.GET)
    public ModelAndView forget() {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("forget");

        return modelAndView;
    }

    @RequestMapping(value = "/logon", method = RequestMethod.POST)
    public ModelAndView logon(@ModelAttribute User user) {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("login");

        if (userService.create(user)) {
            roleService.addToUser(user.getUsername(), "USER");
            modelAndView.addObject("logon", "success");
        } else {
            modelAndView.addObject("logon", "failed");
        }

        return modelAndView;
    }
}
