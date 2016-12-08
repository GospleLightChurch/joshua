/**
 * 后端用户详细信息页面
 * Created by y27chen on 2016/10/31.
 */

import AdminBasePage from "../../joshua-admin-base-page";
import Dialog from "../../component/dialog";

export default class AdminUserDetailsPage extends AdminBasePage {
    init() {
        let page = this;

        $(".ui.admin.user.role.add.modal .dropdown").dropdown();

        $(".ui.admin.user.remove.role").on("click", function () {
            page.removeRole($(this).data("username"), $(this).data("role"));
        });

        $(".ui.admin.user.add.role.button").on("click", function () {
            $(".ui.admin.user.role.add.modal").modal("show");
        });

        $(".ui.admin.user.role.add.modal").modal({
            closeable: false,
            onApprove: function () {
                page.addRole($(".ui.admin.user.form input[name='username']").val(), $(".ui.admin.user.role.add.modal .ui.dropdown").dropdown("get value"));
            },
            onDeny: function () {
                return true;
            }
        });
    }

    addRole(username, role) {
        if (role) {
            var formData = new FormData();
            formData.append("username", username);
            formData.append("role", role);
            formData.append("_csrf", $(".ui.admin.user.form input[name='_csrf']").val());

            $.ajax({
                url: "/api/role/add",
                type: "post",
                data: formData,
                processData: false,
                contentType: false,
                success: function (status) {
                    if (status) {
                        location.reload();
                    } else {
                        new Dialog("添加角色", "添加角色失败").error();
                    }
                },
                error: function () {
                    new Dialog("添加角色", "添加角色失败").error();
                }
            });
        } else {
            alert("请选择角色");
        }
    }

    removeRole(username, role) {
        new Dialog("移除角色", "确定要移除角色吗？", function () {
            var formData = new FormData();
            formData.append("username", username);
            formData.append("role", role);
            formData.append("_csrf", $(".ui.admin.user.form input[name='_csrf']").val());

            $.ajax({
                url: "/api/role/remove",
                type: "post",
                data: formData,
                processData: false,
                contentType: false,
                success: function (status) {
                    if (status) {
                        location.reload();
                    } else {
                        new Dialog("移除角色", "移除角色失败").error();
                    }
                },
                error: function () {
                    new Dialog("移除角色", "移除角色失败").error();
                }
            });
        }).confirm();
    }
}

$(document).ready(()=> {
    var page = new AdminUserDetailsPage();
    page.init();
});