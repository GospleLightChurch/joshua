package org.gyt.web.api.utils;

import org.gyt.web.model.Article;
import org.gyt.web.model.ArticleStatus;
import org.springframework.stereotype.Component;

/**
 * 文章工具
 * Created by y27chen on 2016/10/18.
 */
@Component
public class ArticleUtils {

    /**
     * 判断文章是否能在前端显示
     *
     * @param article 目标文章
     * @return 如果文章没有被禁用并且已经是发布状态则返回true
     */
    public boolean isAvailable(Article article) {
        return article != null && !article.isDisable() && article.getStatus().equals(ArticleStatus.PUBLISHED);
    }
}
