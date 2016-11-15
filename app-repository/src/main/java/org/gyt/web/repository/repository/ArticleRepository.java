package org.gyt.web.repository.repository;

import org.gyt.web.model.Article;
import org.gyt.web.model.ArticleStatus;
import org.gyt.web.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findByAuthor(Pageable pageable, User author);

    Page<Article> findByStatus(Pageable pageable, ArticleStatus status);

    Page<Article> findByAuthorAndStatus(Pageable pageable, User author, ArticleStatus status);
}
