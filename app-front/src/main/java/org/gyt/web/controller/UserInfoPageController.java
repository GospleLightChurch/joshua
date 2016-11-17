package org.gyt.web.controller;

import org.gyt.web.core.service.UserService;
import org.gyt.web.core.utils.ModelAndViewUtils;
import org.gyt.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户信息页面路由器
 * Created by y27chen on 2016/7/12.
 */
@RestController
public class UserInfoPageController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelAndViewUtils modelAndViewUtils;

    @RequestMapping("/center")
    public ModelAndView center() {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("user-center");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
