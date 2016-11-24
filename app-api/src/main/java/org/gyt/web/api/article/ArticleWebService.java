package org.gyt.web.api.article;

import org.gyt.web.core.service.ArticleService;
import org.gyt.web.core.utils.OperationResponse;
import org.gyt.web.core.utils.OperationResponseFactory;
import org.gyt.web.model.Article;
import org.gyt.web.model.ArticleStatus;
import org.gyt.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

/**
 * 文章web接口
 * Created by Administrator on 2016/9/18.
 */
@RestController
@RequestMapping("/api")
public class ArticleWebService {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/article", method = RequestMethod.POST)
    public OperationResponse save(@ModelAttribute Article article, @RequestParam(required = false) MultipartFile file) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            if (null != file) {
                article.setCover(file.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (article.getId() == null || article.getId() <= 0) {
            article.setAuthor(user);
            article.setCreatedDate(new Date());
        } else {
            Article src = articleService.get(article.getId());
            article.setAuthor(src.getAuthor());
            article.setCreatedDate(src.getCreatedDate());

            if (article.getCover() == null && src.getCover() != null) {
                article.setCover(src.getCover());
            }

            if (src.getStatus().equals(ArticleStatus.AUDITING)) {
                return OperationResponseFactory.error("该文章已经在审核中，不能修改在审核中的文章");
            }

            if (src.getStatus().equals(ArticleStatus.PUBLISHED)) {
                return OperationResponseFactory.error("该文章已经发布，不能修改已经发布的文章");
            }
        }


        article.setLastModifiedTime(new Date());
        article.setLastModifiedUser(user);

        article = articleService.createOrUpdate(article);

        return article != null ? OperationResponseFactory.ok(article.getId().toString()) : OperationResponseFactory.error("文章保存失败");
    }

    @RequestMapping(value = "/article/{id}", method = RequestMethod.POST)
    public OperationResponse manage(@PathVariable Long id, @RequestParam String type) {
        if (type.equalsIgnoreCase("delete")) {
            return delete(id);
        } else if (type.equalsIgnoreCase("audit")) {
            return audit(id);
        } else if (type.equalsIgnoreCase("publish")) {
            return publish(id);
        } else if (type.equalsIgnoreCase("reject")) {
            return reject(id);
        } else if (type.equalsIgnoreCase("enable")) {
            return enable(id);
        } else if (type.equalsIgnoreCase("disable")) {
            return disable(id);
        }
        return OperationResponseFactory.error("未知操作");
    }

    public OperationResponse delete(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Article article = articleService.get(id);

        if (article != null && article.getAuthor() != null && article.getAuthor().getUsername().equals(user.getUsername())) {

            if (article.getStatus().equals(ArticleStatus.AUDITING)) {
                return OperationResponseFactory.error("该文章已经在审核中，不能删除在审核中的文章");
            }

            if (article.getStatus().equals(ArticleStatus.PUBLISHED)) {
                return OperationResponseFactory.error("该文章已经发布，不能删除已经发布的文章");
            }

            article.setAuthor(null);
            article.setFellowship(null);
            return !articleService.createOrUpdate(article).isDisable() ? OperationResponseFactory.ok("") : OperationResponseFactory.error("删除文章失败");
        }

        return OperationResponseFactory.error("文章不存在或者已经删除");
    }


    public OperationResponse audit(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Article article = articleService.get(id);

        if (article != null && article.getAuthor().getUsername().equals(user.getUsername())) {
            if (article.getStatus().equals(ArticleStatus.PUBLISHED)) {
                return OperationResponseFactory.error("该文章已经发布");
            }

            if (article.getStatus().equals(ArticleStatus.AUDITING)) {
                return OperationResponseFactory.error("该文章已经在审核中");
            }

            article.setStatus(ArticleStatus.AUDITING);
            return articleService.createOrUpdate(article).getStatus().equals(ArticleStatus.AUDITING) ? OperationResponseFactory.ok("") : OperationResponseFactory.error("更新状态失败");
        }

        return OperationResponseFactory.error("文章不存在或者权限不够");
    }

    public OperationResponse publish(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Article article = articleService.get(id);

        if (article != null && article.getStatus().equals(ArticleStatus.AUDITING) && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MANAGE_ARTICLE"))) {
            article.setAuditor(user);
            article.setStatus(ArticleStatus.PUBLISHED);
            article.setLastModifiedTime(new Date());
            if (articleService.createOrUpdate(article).getStatus().equals(ArticleStatus.PUBLISHED)) {
                return OperationResponseFactory.ok("");
            }
        }

        return OperationResponseFactory.error("发布文章失败");
    }

    public OperationResponse reject(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Article article = articleService.get(id);

        if (article != null && article.getStatus().equals(ArticleStatus.AUDITING) && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MANAGE_ARTICLE"))) {
            article.setAuditor(user);
            article.setStatus(ArticleStatus.REJECTED);
            if (articleService.createOrUpdate(article).getStatus().equals(ArticleStatus.REJECTED)) {
                return OperationResponseFactory.ok("");
            }
        }

        return OperationResponseFactory.error("驳回文章失败");
    }

    public OperationResponse disable(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Article article = articleService.get(id);

        if (article != null && !article.isDisable() && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MANAGE_ARTICLE"))) {
            article.setDisable(true);
            if (articleService.createOrUpdate(article).isDisable()) {
                return OperationResponseFactory.ok("");
            }
        }

        return OperationResponseFactory.error("文章不存在或者已经禁用");
    }

    public OperationResponse enable(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Article article = articleService.get(id);

        if (article != null && article.isDisable() && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MANAGE_ARTICLE"))) {

            article.setDisable(false);
            if (!articleService.createOrUpdate(article).isDisable()) {
                return OperationResponseFactory.ok("");
            }
        }

        return OperationResponseFactory.error("文章不存在或者已经激活");
    }
}
