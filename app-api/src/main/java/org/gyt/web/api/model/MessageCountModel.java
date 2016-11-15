package org.gyt.web.api.model;

/**
 * 留言计数器，用于统计以下信息
 * - 所有留言个数
 * - 建议留言个数
 * - 咨询留言个数
 * - 代祷留言个数
 * Created by y27chen on 2016/9/27.
 */
public class MessageCountModel {

    private Long allCount;
    private Long adviseCount;
    private Long questionCount;
    private Long suffrageCount;

    public MessageCountModel() {
    }

    public Long getAllCount() {
        return allCount;
    }

    public void setAllCount(Long allCount) {
        this.allCount = allCount;
    }

    public Long getAdviseCount() {
        return adviseCount;
    }

    public void setAdviseCount(Long adviseCount) {
        this.adviseCount = adviseCount;
    }

    public Long getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Long questionCount) {
        this.questionCount = questionCount;
    }

    public Long getSuffrageCount() {
        return suffrageCount;
    }

    public void setSuffrageCount(Long suffrageCount) {
        this.suffrageCount = suffrageCount;
    }
}
