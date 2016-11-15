package org.gyt.web.api.model;

/**
 * 用户计数器，用于统计以下信息
 * - 系统管理员人数
 * - 网站编辑人数
 * - 团契管理员人数
 * - 资源管理员人数
 * - 团契成员人数
 * - 注册用户人数
 * Created by y27chen on 2016/9/27.
 */
public class UserCountModel {

    private Long adminCount;
    private Long editorCount;
    private Long adminFSCount;
    private Long adminRECount;
    private Long memberCount;
    private Long userCount;

    public UserCountModel() {
    }

    public Long getAdminCount() {
        return adminCount;
    }

    public void setAdminCount(Long adminCount) {
        this.adminCount = adminCount;
    }

    public Long getEditorCount() {
        return editorCount;
    }

    public void setEditorCount(Long editorCount) {
        this.editorCount = editorCount;
    }

    public Long getAdminFSCount() {
        return adminFSCount;
    }

    public void setAdminFSCount(Long adminFSCount) {
        this.adminFSCount = adminFSCount;
    }

    public Long getAdminRECount() {
        return adminRECount;
    }

    public void setAdminRECount(Long adminRECount) {
        this.adminRECount = adminRECount;
    }

    public Long getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Long memberCount) {
        this.memberCount = memberCount;
    }

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }
}
