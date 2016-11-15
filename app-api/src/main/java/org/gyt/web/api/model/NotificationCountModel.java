package org.gyt.web.api.model;

/**
 * 通知计数器，用于统计以下信息
 * - 所有通知个数
 * - 当前通知个数
 * - 过期通知个数
 * Created by y27chen on 2016/9/27.
 */
public class NotificationCountModel {

    private Long allCount;
    private Long currentCount;
    private Long expiredCount;

    public NotificationCountModel() {
    }

    public Long getAllCount() {
        return allCount;
    }

    public void setAllCount(Long allCount) {
        this.allCount = allCount;
    }

    public Long getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(Long currentCount) {
        this.currentCount = currentCount;
    }

    public Long getExpiredCount() {
        return expiredCount;
    }

    public void setExpiredCount(Long expiredCount) {
        this.expiredCount = expiredCount;
    }
}
