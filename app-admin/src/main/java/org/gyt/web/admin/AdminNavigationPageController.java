package org.gyt.web.admin;

import org.gyt.web.api.service.NavigationService;
import org.gyt.web.api.utils.ModelAndViewUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 后台页面路由器
 * Created by Administrator on 2016/9/16.
 */
@RestController
@RequestMapping("/admin")
public class AdminNavigationPageController {

    @Autowired
    private NavigationService navigationService;

    @Autowired
    private ModelAndViewUtils modelAndViewUtils;

    @RequestMapping("/navigation")
    public ModelAndView primaryPage() {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-navigation");
        modelAndView.addObject("subtitle", "导航栏管理");
        modelAndView.addObject("items", navigationService.getAll());
        return modelAndView;
    }
}
