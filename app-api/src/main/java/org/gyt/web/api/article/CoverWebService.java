package org.gyt.web.api.article;

import org.gyt.web.core.service.ArticleService;
import org.gyt.web.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/article/cover")
public class CoverWebService {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/{id}")
    public byte[] save(@PathVariable Long id) {
        Article article = articleService.get(id);

        if (null != article && !article.isDisable()) {
            return article.getCover();
        }

        return null;
    }

}
