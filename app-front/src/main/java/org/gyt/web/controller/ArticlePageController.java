package org.gyt.web.controller;

import org.gyt.web.core.utils.ModelAndViewUtils;
import org.gyt.web.core.utils.PaginationComponent;
import org.gyt.web.model.Article;
import org.gyt.web.model.ArticleStatus;
import org.gyt.web.model.User;
import org.gyt.web.repository.repository.ArticleRepository;
import org.gyt.web.repository.repository.FellowshipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

/**
 * 前端文章页面路由器
 * Created by Administrator on 2016/9/16.
 */
@RestController
public class ArticlePageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticlePageController.class);

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private FellowshipRepository fellowshipRepository;

    @Autowired
    private ModelAndViewUtils modelAndViewUtils;

    @Autowired
    private PaginationComponent paginationComponent;

    @RequestMapping(value = "/center/article", method = RequestMethod.GET)
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
            LOGGER.info("访问文章 {}", id);
        } else {
            assembleModal(modelAndView, article);
            LOGGER.info("访问文章 {} {}", id, article.getTitle());
        }

        return modelAndView;
    }

    private void assembleModal(ModelAndView modelAndView, Article article) {
        modelAndView.addObject("title", String.format("%s_%s_基督教光音堂", article.getTitle(), article.getFellowship().getDisplayName()));
        modelAndView.addObject("description", String.format("%s_%s_基督教光音堂", article.getTitle(), article.getFellowship().getDisplayName()));
        modelAndView.addObject("item", article);
        modelAndView.addObject("user", article.getAuthor());

        modelAndView.addObject("latestItems", article.getFellowship().getArticles().stream()
                .filter(sourceArticle -> sourceArticle.getStatus().equals(ArticleStatus.PUBLISHED) && !sourceArticle.getId().equals(article.getId()))
                .sorted((o1, o2) -> o2.getLastModifiedTime().compareTo(o1.getLastModifiedTime()))
                .limit(5)
                .collect(Collectors.toList()));
        modelAndView.addObject("hotItems", articleRepository.findTop5ByOrderByPageViewDesc());
        increasePageView(article);
    }

    private void increasePageView(Article article) {
        if (article.getPageView() == null) {
            article.setPageView(1L);
        } else {
            article.setPageView(article.getPageView() + 1);
        }
        articleRepository.save(article);
    }
}
