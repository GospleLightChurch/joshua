package org.gyt.web.admin;

import org.apache.commons.lang3.StringUtils;
import org.gyt.web.api.service.NotificationService;
import org.gyt.web.api.utils.ModelAndViewUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

/**
 * 后台页面路由器
 * Created by Administrator on 2016/9/16.
 */
@RestController
@RequestMapping("/admin")
public class AdminNotificationPageController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ModelAndViewUtils modelAndViewUtils;

    @RequestMapping("/notification")
    public ModelAndView tablePage(
            @RequestParam(required = false) String type
    ) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-notification");

        if (StringUtils.isEmpty(type)) {
            modelAndView.addObject("items", notificationService.getAll());
            modelAndView.addObject("subtitle", "所有通知");
        } else if (type.equalsIgnoreCase("ACTIVATE")) {
            modelAndView.addObject("items", notificationService.getActivate());
            modelAndView.addObject("subtitle", "已激活通知");
        } else if (type.equalsIgnoreCase("EXPIRED")) {
            modelAndView.addObject("items", notificationService.getExpired());
            modelAndView.addObject("subtitle", "已过期通知");
        } else {
            modelAndView.addObject("items", new ArrayList<>());
            modelAndView.addObject("subtitle", "未知类型");
        }

        return modelAndView;
    }
}
