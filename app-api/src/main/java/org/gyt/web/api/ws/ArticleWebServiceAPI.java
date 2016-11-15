package org.gyt.web.api.ws;

import org.gyt.web.api.service.ArticleService;
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
@RequestMapping("/api/article")
public class ArticleWebServiceAPI {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute Article article, @RequestParam(required = false) MultipartFile file) {
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
                return "该文章已经在审核中，不能修改在审核中的文章";
            }

            if (src.getStatus().equals(ArticleStatus.PUBLISHED)) {
                return "该文章已经发布，不能修改已经发布的文章";
            }
        }


        article.setLastModifiedTime(new Date());
        article.setLastModifiedUser(user);

        article = articleService.createOrUpdate(article);

        return article != null ? article.getId().toString() : "文章保存失败";
    }

    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public String audit(@RequestParam Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Article article = articleService.get(id);

        if (article != null && article.getAuthor().getUsername().equals(user.getUsername())) {
            if (article.getStatus().equals(ArticleStatus.PUBLISHED)) {
                return "该文章已经发布";
            }

            if (article.getStatus().equals(ArticleStatus.AUDITING)) {
                return "该文章已经在审核中";
            }

            article.setStatus(ArticleStatus.AUDITING);
            return articleService.createOrUpdate(article).getStatus().equals(ArticleStatus.AUDITING) ? "success" : "更新状态失败";
        }

        return "文章不存在或者权限不够";
    }

    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public String publish(@RequestParam Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Article article = articleService.get(id);

        if (article != null && article.getStatus().equals(ArticleStatus.AUDITING) && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MANAGE_ARTICLE"))) {
            article.setAuditor(user);
            article.setStatus(ArticleStatus.PUBLISHED);
            return articleService.createOrUpdate(article).getStatus().equals(ArticleStatus.PUBLISHED) ? "success" : "发布文章失败";
        }

        return "发布文章失败";
    }

    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    public String reject(@RequestParam Long id, @RequestParam(required = false) String message) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Article article = articleService.get(id);

        if (article != null && article.getStatus().equals(ArticleStatus.AUDITING) && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MANAGE_ARTICLE"))) {
            article.setAuditor(user);
            article.setStatus(ArticleStatus.REJECTED);
            article.setAuditComment(message);
            return articleService.createOrUpdate(article).getStatus().equals(ArticleStatus.REJECTED) ? "success" : "驳回文章失败";
        }

        return "驳回文章失败";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Article article = articleService.get(id);

        if (article != null && article.getAuthor() != null && article.getAuthor().getUsername().equals(user.getUsername())) {

            if (article.getStatus().equals(ArticleStatus.AUDITING)) {
                return "该文章已经在审核中，不能删除在审核中的文章";
            }

            if (article.getStatus().equals(ArticleStatus.PUBLISHED)) {
                return "该文章已经发布，不能删除已经发布的文章";
            }

            article.setAuthor(null);
            article.setFellowship(null);
            return !articleService.createOrUpdate(article).isDisable() ? "success" : "删除文章失败";
        }

        return "文章不存在或者已经删除";
    }

    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    public String disable(@RequestParam Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Article article = articleService.get(id);

        if (article != null && !article.isDisable() && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MANAGE_ARTICLE"))) {
            article.setDisable(true);
            return articleService.createOrUpdate(article).isDisable() ? "success" : "禁用文章失败";
        }

        return "文章不存在或者已经禁用";
    }

    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    public String enable(@RequestParam Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Article article = articleService.get(id);

        if (article != null && article.isDisable() && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MANAGE_ARTICLE"))) {

            article.setDisable(false);
            return !articleService.createOrUpdate(article).isDisable() ? "success" : "激活文章失败";
        }

        return "文章不存在或者已经激活";
    }
}
