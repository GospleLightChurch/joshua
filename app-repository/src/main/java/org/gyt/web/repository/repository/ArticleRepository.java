package org.gyt.web.repository.repository;

import org.gyt.web.model.Article;
import org.gyt.web.model.ArticleStatus;
import org.gyt.web.model.Fellowship;
import org.gyt.web.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findByStatusOrderByLastModifiedTimeDesc(Pageable pageable, ArticleStatus status);

    Page<Article> findByAuthorAndStatusOrderByLastModifiedTimeDesc(Pageable pageable, User author, ArticleStatus status);

    Page<Article> findByAuthorOrderByStatusDescLastModifiedTimeDesc(Pageable pageable, User author);

    @Query("select article from Article article where article.fellowship = :fellowship and article.status = 'PUBLISHED'")
    Page<Article> findByFellowshipOrderByLastModifiedTimeDesc(Pageable pageable, @Param("fellowship") Fellowship fellowship);

    List<Article> findTop5ByOrderByPageViewDesc();
}
