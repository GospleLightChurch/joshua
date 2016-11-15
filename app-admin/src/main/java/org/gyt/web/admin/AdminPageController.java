package org.gyt.web.admin;

import org.gyt.web.api.repository.ArticleRepository;
import org.gyt.web.api.repository.MessageRepository;
import org.gyt.web.api.service.FellowshipService;
import org.gyt.web.api.service.UserService;
import org.gyt.web.api.utils.ModelAndViewUtils;
import org.gyt.web.model.ArticleStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 后台页面路由器
 * Created by Administrator on 2016/9/16.
 */
@RestController
@RequestMapping("/")
public class AdminPageController {

    @Autowired
    private UserService userService;

    @Autowired
    private FellowshipService fellowshipService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ModelAndViewUtils modelAndViewUtils;

    @RequestMapping("admin")
    public ModelAndView home() {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-home");
        modelAndView.addObject("resistedCount", userService.count());
        modelAndView.addObject("fellowshipCount", fellowshipService.getAll().size());
        modelAndView.addObject("articleCount", articleRepository.findAll().stream().filter(article -> !article.isDisable() && article.getStatus().equals(ArticleStatus.PUBLISHED)).count());
        modelAndView.addObject("messageCount", messageRepository.count());
        return modelAndView;
    }

    @RequestMapping("admin/")
    public ModelAndView home2() {
        return home();
    }
}
