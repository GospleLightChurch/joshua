package org.gyt.web.api.service.impl;

import org.gyt.web.api.repository.ArticleRepository;
import org.gyt.web.api.service.ArticleService;
import org.gyt.web.model.Article;
import org.gyt.web.model.ArticleStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private static final int MAX_HOMEPAGE_ARTICLE_SIZE = 5;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Article get(Long id) {
        return articleRepository.findOne(id);
    }

    @Override
    public List<Article> getFromFellowship(String name) {
        return articleRepository.findAll().stream().filter(article -> article.isDisable() && article.getFellowship() != null && article.getFellowship().getName().equals(name)).collect(Collectors.toList());
    }

    @Override
    public List<Article> getFellowshipPublishedArticles(String name) {
        return articleRepository.findAll().stream().filter(article -> article.getStatus().equals(ArticleStatus.PUBLISHED) && article.getFellowship() != null && article.getFellowship().getName().equals(name)).collect(Collectors.toList());
    }

    @Override
    public List<Article> getFromUser(String username) {
        return articleRepository.findAll().stream().filter(article -> (article.getAuthor() != null && article.getAuthor().getUsername().equals(username)) || (article.getAuditor() != null && article.getAuditor().getUsername().equals(username))).collect(Collectors.toList());
    }

    @Override
    public List<Article> getLatestArticles() {
        List<Article> articleList = articleRepository.findAll().stream().filter(article -> !article.isDisable() && article.getStatus().equals(ArticleStatus.PUBLISHED)).collect(Collectors.toList());
        articleList.sort((o1, o2) -> o2.getLastModifiedTime().compareTo(o1.getLastModifiedTime()));

        if (articleList.size() > MAX_HOMEPAGE_ARTICLE_SIZE * 2) {
            return articleList.subList(0, MAX_HOMEPAGE_ARTICLE_SIZE * 2);
        }

        return articleList;
    }

    @Override
    public List<Article> getChurchArticles() {
        List<Article> articleList = articleRepository.findAll().stream().filter(
                article -> !article.isDisable() && article.getStatus().equals(ArticleStatus.PUBLISHED) && (
                        article.getFellowship().getName().equals("worship") ||
                                article.getFellowship().getName().equals("testimony") ||
                                article.getFellowship().getName().equals("report") ||
                                article.getFellowship().getName().equals("public") ||
                                article.getFellowship().getName().equals("suffrage") ||
                                article.getFellowship().getName().equals("recommend")
                )
        ).collect(Collectors.toList());
        articleList.sort((o1, o2) -> o2.getLastModifiedTime().compareTo(o1.getLastModifiedTime()));

        if (articleList.size() > MAX_HOMEPAGE_ARTICLE_SIZE) {
            return articleList.subList(0, MAX_HOMEPAGE_ARTICLE_SIZE);
        }

        return articleList;
    }

    @Override
    public List<Article> getFellowshipArticles() {
        List<Article> articleList = articleRepository.findAll().stream().filter(
                article -> !article.isDisable() && article.getStatus().equals(ArticleStatus.PUBLISHED) && (
                        !article.getFellowship().getName().equals("worship") &&
                                !article.getFellowship().getName().equals("testimony") &&
                                !article.getFellowship().getName().equals("report") &&
                                !article.getFellowship().getName().equals("public") &&
                                !article.getFellowship().getName().equals("suffrage") &&
                                !article.getFellowship().getName().equals("recommend")
                )
        ).collect(Collectors.toList());
        articleList.sort((o1, o2) -> o2.getLastModifiedTime().compareTo(o1.getLastModifiedTime()));

        if (articleList.size() > MAX_HOMEPAGE_ARTICLE_SIZE) {
            return articleList.subList(0, MAX_HOMEPAGE_ARTICLE_SIZE);
        }

        return articleList;
    }

    @Override
    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    @Override
    public Article createOrUpdate(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public boolean enable(Long id) {
        Article article = articleRepository.findOne(id);

        if (null != article && article.isDisable()) {
            article.setDisable(false);
            articleRepository.save(article);
        }

        return false;
    }

    @Override
    public boolean isEnabled(Long id) {
        Article article = articleRepository.findOne(id);
        return null != article && article.isDisable();
    }

    @Override
    public boolean disable(Long id) {
        Article article = articleRepository.findOne(id);

        if (null != article && !article.isDisable()) {
            article.setDisable(true);
            articleRepository.save(article);
            return true;
        }

        return false;
    }

    @Override
    public boolean audit(Long id) {
        Article article = articleRepository.findOne(id);

        if (null != article && !article.isDisable()) {
            article.setStatus(ArticleStatus.AUDITING);
            articleRepository.save(article);
            return true;
        }

        return false;
    }

    @Override
    public boolean publish(Long id) {
        Article article = articleRepository.findOne(id);

        if (null != article && !article.isDisable()) {
            article.setStatus(ArticleStatus.PUBLISHED);
            articleRepository.save(article);
            return true;
        }

        return false;
    }

    @Override
    public Article increasePageView(Article article) {
        if (article.getPageView() == null) {
            article.setPageView(1L);
        } else {
            article.setPageView(article.getPageView() + 1);
        }
        return createOrUpdate(article);
    }

    @Override
    public Article increasePageView(Long id) {
        Article article = get(id);
        return article == null ? null : increasePageView(article);
    }
}
