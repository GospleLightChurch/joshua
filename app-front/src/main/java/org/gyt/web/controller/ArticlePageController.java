package org.gyt.web.controller;

import org.gyt.web.core.utils.ModelAndViewUtils;
import org.gyt.web.core.utils.PaginationComponent;
import org.gyt.web.model.Article;
import org.gyt.web.model.ArticleStatus;
import org.gyt.web.model.User;
import org.gyt.web.repository.repository.ArticleRepository;
import org.gyt.web.repository.repository.FellowshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 前端文章页面路由器
 * Created by Administrator on 2016/9/16.
 */
@RestController
public class ArticlePageController {
 
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private FellowshipRepository fellowshipRepository;

    @Autowired
    private ModelAndViewUtils modelAndViewUtils;

    @Autowired
    private PaginationComponent paginationComponent;

    @RequestMapping("/center/article")
    public ModelAndView centerPage(Pageable pageable) {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("center-article");
        User user = modelAndViewUtils.getCurrentUser();
        Page<Article> articlePage = articleRepository.findByAuthorOrderByStatusDescLastModifiedTimeDesc(pageable, user);
        modelAndView.addObject("items", articlePage.getContent());
        paginationComponent.addPagination(modelAndView, articlePage, "/center/article");
        return modelAndView;
    }

    @RequestMapping("/center/article/new")
    public ModelAndView newEditorPage() {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("article-editor");
        User user = modelAndViewUtils.getCurrentUser();
        modelAndView.addObject("article", new Article());
        modelAndView.addObject("fellowship", fellowshipRepository.findByUser(user));
        return modelAndView;
    }

    @RequestMapping("/article/{id}/edit")
    public ModelAndView editPage(@PathVariable Long id) {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("article-editor");
        User user = modelAndViewUtils.getCurrentUser();
        Article article = articleRepository.findOne(id);

        if (user == null || article == null || !article.getAuthor().equals(user)) {
            modelAndViewUtils.convertTo404(modelAndView, "您访问的文章不存在或者没有权限");
        } else {
            modelAndView.addObject("article", article);
            modelAndView.addObject("fellowship", fellowshipRepository.findByUser(user));
        }
        return modelAndView;
    }

    @RequestMapping("/article/{id}/preview")
    public ModelAndView previewPage(@PathVariable Long id) {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("article");
        User user = modelAndViewUtils.getCurrentUser();
        Article article = articleRepository.findOne(id);

        if (user == null || article == null || !article.getAuthor().equals(user)) {
            modelAndViewUtils.convertTo404(modelAndView, "您访问的文章不存在或者没有权限");
        } else {
            modelAndView.addObject("item", article);
            modelAndView.addObject("fellowship", fellowshipRepository.findByUser(user));
        }
        return modelAndView;
    }

    @RequestMapping("/article/{id}.html")
    public ModelAndView detailsHtmlPage(@PathVariable Long id) {
        return getDetails(id);
    }

    @RequestMapping("/article/{id}")
    public ModelAndView detailsPage(@PathVariable Long id) {
        return getDetails(id);
    }

    private ModelAndView getDetails(Long id) {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("article");
        Article article = articleRepository.findOne(id);

        if (null == article || article.isDisable() || !article.getStatus().equals(ArticleStatus.PUBLISHED)) {
            modelAndViewUtils.convertTo404(modelAndView, "文章不存在或者未发布");
        } else {
            assembleModal(modelAndView, article);
        }

        return modelAndView;
    }

    private void assembleModal(ModelAndView modelAndView, Article article) {
        modelAndView.addObject("title", article.getTitle());
        modelAndView.addObject("description", String.format("%s 光音堂", article.getTitle()));
        modelAndView.addObject("item", article);
        modelAndView.addObject("user", article.getAuthor());

        if (article.getPageView() == null) {
            article.setPageView(1L);
        } else {
            article.setPageView(article.getPageView() + 1);
        }
        articleRepository.save(article);
    }
}
