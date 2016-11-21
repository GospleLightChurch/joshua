package org.gyt.web.admin;

import org.gyt.web.core.service.FellowshipService;
import org.gyt.web.core.utils.ModelAndViewUtils;
import org.gyt.web.core.utils.PaginationComponent;
import org.gyt.web.model.Article;
import org.gyt.web.model.ArticleStatus;
import org.gyt.web.model.Fellowship;
import org.gyt.web.model.User;
import org.gyt.web.repository.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Set;

/**
 * 后台页面路由器
 * Created by Administrator on 2016/9/16.
 */
@RestController
@RequestMapping("/admin/article")
public class AdminArticlePageController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private FellowshipService fellowshipService;

    @Autowired
    private ModelAndViewUtils modelAndViewUtils;

    @Autowired
    private PaginationComponent paginationComponent;

    @RequestMapping("/audit")
    public ModelAndView getAudit(Pageable pageable) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-article");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<Article> articlePage;
        if (user.getRoles().stream().anyMatch(role -> role.getAuthorities().stream().anyMatch(s -> s.equals("ROLE_MANAGE_ARTICLE")))) {
            articlePage = articleRepository.findByStatusOrderByLastModifiedTimeDesc(pageable, ArticleStatus.AUDITING);
        } else {
            articlePage = articleRepository.findByAuthorAndStatusOrderByLastModifiedTimeDesc(pageable, user, ArticleStatus.AUDITING);
        }
        modelAndView.addObject("type", "audit");
        modelAndView.addObject("items", articlePage.getContent());
        paginationComponent.addPagination(modelAndView, articlePage, "/admin/article/audit");
        return modelAndView;
    }

    @RequestMapping("/publish")
    public ModelAndView getPublish(Pageable pageable) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-article");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<Article> articlePage;
        if (user.getRoles().stream().anyMatch(role -> role.getAuthorities().stream().anyMatch(s -> s.equals("ROLE_MANAGE_ARTICLE")))) {
            articlePage = articleRepository.findByStatusOrderByLastModifiedTimeDesc(pageable, ArticleStatus.PUBLISHED);
        } else {
            articlePage = articleRepository.findByAuthorAndStatusOrderByLastModifiedTimeDesc(pageable, user, ArticleStatus.PUBLISHED);
        }
        modelAndView.addObject("type", "publish");
        modelAndView.addObject("items", articlePage.getContent());
        paginationComponent.addPagination(modelAndView, articlePage, "/admin/article/publish");
        return modelAndView;
    }

    @RequestMapping("/{id}/audit")
    public ModelAndView auditArticlePage(
            @PathVariable Long id
    ) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-article-audit");
        modelAndView.addObject("title", "审核文章");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Article article = articleRepository.findOne(id);

        if (article == null) {
            modelAndView.addObject("error", "文章不存在");
        } else {
            modelAndView.addObject("title", String.format("光音堂后台 - 文章审核 - %s", article.getTitle()));
            modelAndView.addObject("subtitle", article.getTitle());
            modelAndView.addObject("item", article);
        }

        return modelAndView;
    }
}
