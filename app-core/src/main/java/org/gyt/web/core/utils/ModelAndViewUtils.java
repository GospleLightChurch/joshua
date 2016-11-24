package org.gyt.web.core.utils;

import org.gyt.web.core.service.ArticleService;
import org.gyt.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

/**
 * 模型和视图工具
 * Created by y27chen on 2016/9/20.
 */
@Component
public class ModelAndViewUtils {

    @Autowired
    private CountModelComponent countModelComponent;

    @Autowired
    private ArticleService articleService;

    private ModelAndViewUtils() {
    }

    public ModelAndView newModelAndView(String viewName) {
        ModelAndView modelAndView = new ModelAndView(viewName);
        return modelAndView;
    }

    public ModelAndView newAdminModelAndView(String viewName) {
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("userCountModel", countModelComponent.getUserCountModel());
        modelAndView.addObject("articleCountModel", countModelComponent.getArticleCountModel());
        modelAndView.addObject("notificationCountModel", countModelComponent.getNotificationCountModel());
        modelAndView.addObject("messageCountModel", countModelComponent.getMessageCountModel());
        return modelAndView;
    }

    public void convertTo404(ModelAndView modelAndView) {
        convertTo404(modelAndView, "您访问的资源不存在，或者已经被移到别处");
    }

    public void convertTo404(ModelAndView modelAndView, String message) {
        modelAndView.setViewName("error/404");
        modelAndView.addObject("message", message);
        modelAndView.addObject("articles", articleService.getLatestArticles());
    }

    public User getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return null;
    }
}
