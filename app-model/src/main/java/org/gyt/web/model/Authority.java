package org.gyt.web.model;

/**
 * 权限定义
 * Created by Administrator on 2016/9/12.
 */
public enum Authority {

    /**
     * 后台登录权限
     * - 可以登录到后台
     */
    ROLE_ADMIN_ACCESS,

    /**
     * 用户角色管理
     * - 添加角色
     * - 移除角色
     */
    ROLE_MANAGE_USER_ROLE,

    /**
     * 用户状态管理
     * - 锁定、解锁用户
     * - 禁用、激活用户
     */
    ROLE_MANAGE_USER_STATUS,

    /**
     * 管理静态页面权限：
     * - 首页大图
     * - 首页介绍
     * - 其他静态页面
     */
    ROLE_MANAGE_STATIC_PAGE,

    /**
     * 管理导航栏权限
     * - 添加导航栏
     * - 移除导航栏
     * - 调整导航栏
     */
    ROLE_MANAGE_NAVIGATION,

    /**
     * 管理网站通知权限：
     * - 发布网站通知
     * - 删除网站通知
     */
    ROLE_MANAGE_NOTIFICATION,

    /**
     * 管理留言信息权限：
     * - 查看留言信息
     * - 删除留言信息
     */
    ROLE_MANAGE_MESSAGE,

    /**
     * 管理资源权限：
     * - 上传资源
     * - 删除资源
     */
    ROLE_MANAGE_RESOURCE,

    /**
     * 管理团契权限：
     * - 创建团契
     * - 禁用、激活团契
     */
    ROLE_MANAGE_FELLOWSHIP,

    /**
     * 文章管理权限：
     * - 发布文章
     * - 驳回文章
     * - 禁用文章
     */
    ROLE_MANAGE_ARTICLE,

    /**
     * 媒体资源下载权限：
     * - 下载媒体资源
     */
    ROLE_DOWNLOAD_RESOURCE,

    /**
     * 发布留言权限：
     * - 发布留言信息
     */
    ROLE_SEND_MESSAGE

}
