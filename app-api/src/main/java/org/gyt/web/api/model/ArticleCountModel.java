package org.gyt.web.api.model;

/**
 * 文章计数器，用于统计以下信息
 * - 我的文章个数
 * - 草稿箱个数
 * - 待审核文章个数
 * - 已发布文章个数
 * - 驳回文章个数
 * Created by y27chen on 2016/9/27.
 */
public class ArticleCountModel {

    private Long myArticleCount;
    private Long rawArticleCount;
    private Long auditArticleCount;
    private Long publishArticleCount;
    private Long rejectArticleCount;

    public ArticleCountModel() {
    }

    public Long getMyArticleCount() {
        return myArticleCount;
    }

    public void setMyArticleCount(Long myArticleCount) {
        this.myArticleCount = myArticleCount;
    }

    public Long getRawArticleCount() {
        return rawArticleCount;
    }

    public void setRawArticleCount(Long rawArticleCount) {
        this.rawArticleCount = rawArticleCount;
    }

    public Long getAuditArticleCount() {
        return auditArticleCount;
    }

    public void setAuditArticleCount(Long auditArticleCount) {
        this.auditArticleCount = auditArticleCount;
    }

    public Long getPublishArticleCount() {
        return publishArticleCount;
    }

    public void setPublishArticleCount(Long publishArticleCount) {
        this.publishArticleCount = publishArticleCount;
    }

    public Long getRejectArticleCount() {
        return rejectArticleCount;
    }

    public void setRejectArticleCount(Long rejectArticleCount) {
        this.rejectArticleCount = rejectArticleCount;
    }
}
