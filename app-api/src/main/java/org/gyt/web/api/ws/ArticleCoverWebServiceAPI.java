package org.gyt.web.api.ws;

import org.gyt.web.api.service.ArticleService;
import org.gyt.web.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章web接口
 * Created by Administrator on 2016/9/18.
 */
@RestController
public class ArticleCoverWebServiceAPI {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/cover/{id}")
    public byte[] save(@PathVariable Long id) {
        Article article = articleService.get(id);

        if (null != article && !article.isDisable()) {
            return article.getCover();
        }

        return null;
    }

}
