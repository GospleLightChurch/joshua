package org.gyt.web.admin;

import org.apache.commons.lang3.StringUtils;
import org.gyt.web.api.service.ArticleService;
import org.gyt.web.api.service.FellowshipService;
import org.gyt.web.api.utils.ModelAndViewUtils;
import org.gyt.web.api.utils.PaginationComponent;
import org.gyt.web.model.Article;
import org.gyt.web.model.ArticleStatus;
import org.gyt.web.model.Fellowship;
import org.gyt.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 后台页面路由器
 * Created by Administrator on 2016/9/16.
 */
@RestController
@RequestMapping("/admin")
public class AdminArticlePageController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private FellowshipService fellowshipService;

    @Autowired
    private ModelAndViewUtils modelAndViewUtils;

    @Autowired
    private PaginationComponent paginationComponent;

    @RequestMapping("/article")
    public ModelAndView tablePage(
            @RequestParam(required = false) String type,
            @RequestParam(required = false, defaultValue = "1") int pageNumber,
            @RequestParam(required = false, defaultValue = "20") int pageSize
    ) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-article");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Article> articleList = articleService.getFromUser(user.getUsername());

        if (StringUtils.isEmpty(type) || type.equalsIgnoreCase("MINE")) {
            modelAndView.addObject("subtitle", "我的文章");
            type = "MINE";
        } else if (type.equalsIgnoreCase("RAW")) {
            articleList = articleList.stream().filter(article -> article.getStatus().equals(ArticleStatus.RAW)).collect(Collectors.toList());
            modelAndView.addObject("subtitle", "草稿箱");
        } else if (type.equalsIgnoreCase("AUDIT")) {
            if (user.getRoles().stream().anyMatch(role -> role.getAuthorities().stream().anyMatch(s -> s.equals("ROLE_MANAGE_ARTICLE")))) {
                articleList = articleService.getAll().stream().filter(article -> article.getStatus().equals(ArticleStatus.AUDITING)).collect(Collectors.toList());
            } else {
                articleList = articleList.stream().filter(article -> article.getStatus().equals(ArticleStatus.AUDITING)).collect(Collectors.toList());
            }
            modelAndView.addObject("subtitle", "待审核文章");
        } else if (type.equalsIgnoreCase("PUBLISH")) {
            if (user.getRoles().stream().anyMatch(role -> role.getAuthorities().stream().anyMatch(s -> s.equals("ROLE_MANAGE_ARTICLE")))) {
                articleList = articleService.getAll().stream().filter(article -> article.getStatus().equals(ArticleStatus.PUBLISHED)).collect(Collectors.toList());
            } else {
                articleList = articleList.stream().filter(article -> article.getStatus().equals(ArticleStatus.PUBLISHED)).collect(Collectors.toList());
            }
            modelAndView.addObject("subtitle", "已发布文章");
        } else if (type.equalsIgnoreCase("REJECT")) {
            articleList = articleList.stream().filter(article -> article.getStatus().equals(ArticleStatus.REJECTED)).collect(Collectors.toList());
            modelAndView.addObject("subtitle", "驳回文章");
        } else {
            articleList = new ArrayList<>();
            modelAndView.addObject("subtitle", "未知类型");
        }

        articleList.sort((o1, o2) -> o2.getLastModifiedTime().compareTo(o1.getLastModifiedTime()));

        modelAndView.addObject("title", String.format("光音堂后台 - %s", modelAndView.getModel().get("subtitle")));
        modelAndView.addObject("type", type);
        modelAndView.addObject("items", paginationComponent.listPagination(articleList, pageNumber, pageSize));
        paginationComponent.addPaginationModel(modelAndView, "/admin/article?type=" + type, articleList.size(), pageNumber, pageSize);
        return modelAndView;
    }

    @RequestMapping("/article/{id}")
    public ModelAndView detailsPage(
            @PathVariable String id
    ) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-article-details");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Article article = articleService.get(Long.valueOf(id));

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
            @PathVariable String id
    ) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-article-editor");
        modelAndView.addObject("title", "编辑文章");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Article article = articleService.get(Long.valueOf(id));

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
            @PathVariable String id
    ) {
        ModelAndView modelAndView = modelAndViewUtils.newAdminModelAndView("adminPages/admin-article-audit");
        modelAndView.addObject("title", "审核文章");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Article article = articleService.get(Long.valueOf(id));

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
