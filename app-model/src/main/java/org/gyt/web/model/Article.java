package org.gyt.web.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 文章实体
 * Created by Administrator on 2016/9/12.
 */
@Entity
@Table
public class Article {

    @Id
    @GeneratedValue
    private Long id;

    private boolean disable;

    @Lob
    private byte[] cover;

    private String title;

    private String description;

    @ManyToOne
    @JoinColumn
    private Fellowship fellowship;

    private Date createdDate;

    private Date lastModifiedTime;

    @Enumerated(value = EnumType.STRING)
    private ArticleStatus status = ArticleStatus.RAW;

    @ManyToOne
    private User author;

    @ManyToOne
    private User lastModifiedUser;

    @ManyToOne
    private User auditor;

    private String auditComment;

    private Long pageView = 0L;

    @Lob
    private String content;

    public Article() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Fellowship getFellowship() {
        return fellowship;
    }

    public void setFellowship(Fellowship fellowship) {
        this.fellowship = fellowship;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public ArticleStatus getStatus() {
        return status;
    }

    public void setStatus(ArticleStatus status) {
        this.status = status;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getLastModifiedUser() {
        return lastModifiedUser;
    }

    public void setLastModifiedUser(User lastModifiedUser) {
        this.lastModifiedUser = lastModifiedUser;
    }

    public User getAuditor() {
        return auditor;
    }

    public void setAuditor(User auditor) {
        this.auditor = auditor;
    }

    public String getAuditComment() {
        return auditComment;
    }

    public void setAuditComment(String auditComment) {
        this.auditComment = auditComment;
    }

    public Long getPageView() {
        return pageView;
    }

    public void setPageView(Long pageView) {
        this.pageView = pageView;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
