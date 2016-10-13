package org.gyt.web.admin;

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
@RequestMapping("/admin/static")
public class AdminStaticPageController {

    @Autowired
    private ModelAndViewUtils modelAndViewUtils;

    @RequestMapping("/home")
    public ModelAndView home() {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/staticHomePage");
        return modelAndView;
    }
}