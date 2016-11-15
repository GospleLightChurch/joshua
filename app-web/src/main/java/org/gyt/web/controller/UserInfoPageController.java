package org.gyt.web.controller;

import org.gyt.web.api.service.UserService;
import org.gyt.web.api.utils.ModelAndViewUtils;
import org.gyt.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("/info")
    public ModelAndView infoPage(
            @RequestParam(required = false) boolean publishSuccess

    ) {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("info");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modelAndView.addObject("user", user);

        if (publishSuccess) {
            modelAndView.addObject("publishSuccess", true);
        }

        return modelAndView;
    }

    @RequestMapping(value = "/password")
    public ModelAndView passwordPage() {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("password");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
