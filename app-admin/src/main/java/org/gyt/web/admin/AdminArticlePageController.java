package org.gyt.web.admin;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * 后台页面路由器
 * Created by Administrator on 2016/9/16.
 */
@RestController
@RequestMapping("/admin")
public class AdminArticlePageController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private FellowshipService fellowshipService;

    @Autowired
    private ModelAndViewUtils modelAndViewUtils;

    @Autowired
    private PaginationComponent paginationComponent;

    @RequestMapping("/article")
    public ModelAndView tablePage(
            @RequestParam(required = false) String type, Pageable pageable
    ) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-article");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Page<Article> articlePage = new PageImpl<>(new ArrayList<>());

        if (StringUtils.isEmpty(type) || type.equalsIgnoreCase("MINE")) {
            modelAndView.addObject("subtitle", "我的文章");
            type = "MINE";
            articlePage = articleRepository.findByAuthor(pageable, user.getUsername());
        } else if (type.equalsIgnoreCase("RAW")) {
            modelAndView.addObject("subtitle", "草稿箱");
            articlePage = articleRepository.findByAuthorAndStatus(pageable, user.getUsername(), ArticleStatus.RAW);
        } else if (type.equalsIgnoreCase("AUDIT")) {
            modelAndView.addObject("subtitle", "待审核文章");
            if (user.getRoles().stream().anyMatch(role -> role.getAuthorities().stream().anyMatch(s -> s.equals("ROLE_MANAGE_ARTICLE")))) {
                articlePage = articleRepository.findByStatus(pageable, ArticleStatus.AUDITING);
            } else {
                articlePage = articleRepository.findByAuthorAndStatus(pageable, user.getUsername(), ArticleStatus.AUDITING);
            }
        } else if (type.equalsIgnoreCase("PUBLISH")) {
            modelAndView.addObject("subtitle", "已发布文章");
            if (user.getRoles().stream().anyMatch(role -> role.getAuthorities().stream().anyMatch(s -> s.equals("ROLE_MANAGE_ARTICLE")))) {
                articlePage = articleRepository.findByStatus(pageable, ArticleStatus.PUBLISHED);
            } else {
                articlePage = articleRepository.findByAuthorAndStatus(pageable, user.getUsername(), ArticleStatus.PUBLISHED);
            }
        } else if (type.equalsIgnoreCase("REJECT")) {
            modelAndView.addObject("subtitle", "驳回文章");
            articlePage = articleRepository.findByAuthorAndStatus(pageable, user.getUsername(), ArticleStatus.REJECTED);
        } else {
            modelAndView.addObject("subtitle", "未知类型");
        }

        modelAndView.addObject("title", String.format("光音堂后台 - %s", modelAndView.getModel().get("subtitle")));
        modelAndView.addObject("type", type);
        modelAndView.addObject("items", articlePage.getContent());
        paginationComponent.addPagination(modelAndView, articlePage, "/admin/article?type=" + type);
        return modelAndView;
    }

    @RequestMapping("/article/{id}")
    public ModelAndView detailsPage(
            @PathVariable Long id
    ) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-article-details");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Article article = articleRepository.findOne(id);

        if (null == article) {
            modelAndView.addObject("error", "文章不存在");
        } else {
            modelAndView.addObject("title", String.format("光音堂后台 - 文章预览 - %s", article.getTitle()));
            modelAndView.addObject("subtitle", article.getTitle());
            modelAndView.addObject("item", article);
            modelAndView.addObject("user", user);
        }

        return modelAndView;
    }

    @RequestMapping("/article/{id}/edit")
    public ModelAndView editArticlePage(
            @PathVariable Long id
    ) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-article-editor");
        modelAndView.addObject("title", "编辑文章");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Article article = articleRepository.findOne(id);

        if (article == null) {
            modelAndView.addObject("error", "文章不存在");
        } else if (!article.getAuthor().getUsername().equals(user.getUsername())) {
            modelAndView.addObject("error", "只能编辑自己的文章");
        } else {
            modelAndView.addObject("title", String.format("光音堂后台 - 文章编辑 - %s", article.getTitle()));
            modelAndView.addObject("subtitle", article.getTitle());
            modelAndView.addObject("item", article);
            modelAndView.addObject("edit", true);
            Set<Fellowship> fellowshipSet = new HashSet<>();
            fellowshipSet.addAll(fellowshipService.getUserOwnerFellowship(user.getUsername()));
            fellowshipSet.addAll(fellowshipService.getUserAdminFellowship(user.getUsername()));
            modelAndView.addObject("fellowship", fellowshipSet);
        }

        return modelAndView;
    }

    @RequestMapping("/article/{id}/audit")
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

    @RequestMapping("/article/new")
    public ModelAndView newArticlePage() {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-article-editor");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (fellowshipService.getUserOwnerFellowship(user.getUsername()).isEmpty() && fellowshipService.getUserAdminFellowship(user.getUsername()).isEmpty()) {
            modelAndView.setViewName("403");
            modelAndView.addObject("message", "抱歉，您还没有任何团契的管理权限，不能发布文章到团契");
            return modelAndView;
        }

        modelAndView.addObject("title", "光音堂后台 - 新建文章");
        modelAndView.addObject("subtitle", "新建文章");
        modelAndView.addObject("item", new Article());

        Set<Fellowship> fellowshipSet = new HashSet<>();
        fellowshipSet.addAll(fellowshipService.getUserOwnerFellowship(user.getUsername()));
        fellowshipSet.addAll(fellowshipService.getUserAdminFellowship(user.getUsername()));
        modelAndView.addObject("fellowship", fellowshipSet);
        return modelAndView;
    }
}
