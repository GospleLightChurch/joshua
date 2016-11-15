package org.gyt.web.controller;

import org.gyt.web.api.service.ArticleService;
import org.gyt.web.api.utils.ModelAndViewUtils;
import org.gyt.web.model.Article;
import org.gyt.web.model.ArticleStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 前端文章页面路由器
 * Created by Administrator on 2016/9/16.
 */
@RestController
@RequestMapping("/article")
public class ArticlePageController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ModelAndViewUtils modelAndViewUtils;

    @RequestMapping("/{id}")
    public ModelAndView detailsPage(
            @PathVariable String id
    ) {
        ModelAndView modelAndView = modelAndViewUtils.newModelAndView("article");
        Article article = articleService.get(Long.valueOf(id));

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

        articleService.increasePageView(article);
    }
}
