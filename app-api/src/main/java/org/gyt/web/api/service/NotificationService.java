package org.gyt.web.api.service;

import org.gyt.web.model.Notification;

import java.util.List;

/**
 * 网站通知API，提供以下接口
 * - 获取所有网站通知
 * - 获取激活哦的网站通知
 * - 获取过期的网站通知
 * - 新建网站通知
 * Created by y27chen on 2016/9/20.
 */
public interface NotificationService {

    /**
     * 获取所有网站通知
     *
     * @return 网站通知列表
     */
    List<Notification> getAll();

    /**
     * 获取所有已经激活的网站通知
     *
     * @return 已经激活的通知列表
     */
    List<Notification> getActivate();

    /**
     * 获取所有已经过期的网站通知
     *
     * @return 已经过期的通知列表
     */
    List<Notification> getExpired();

    /**
     * 发布网站通知
     *
     * @param notification 通知对象
     * @return 如果发布成功返回true，否则返回false
     */
    boolean create(Notification notification);
}
