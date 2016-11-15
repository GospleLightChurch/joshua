package org.gyt.web.api.ws;

import org.gyt.web.api.service.ArticleService;
import org.gyt.web.model.Article;
import org.gyt.web.model.ArticleStatus;
import org.gyt.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章内容web接口
 * Created by Administrator on 2016/9/18.
 */
@RestController
@RequestMapping("/article/content")
public class ArticleContentWebServiceAPI {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String save(@PathVariable Long id) {
        Article article = articleService.get(id);

        if (article == null) {
            return "文章不存在";
        }

        if (article.isDisable()) {
            return "该文章已经被禁用";
        }

        if (article.getStatus().equals(ArticleStatus.PUBLISHED)) {
            return article.getContent();
        }

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (article.getAuthor().equals(user)) {
                return article.getContent();
            }
        }
        return "您没有权限访问该文章";
    }
}
