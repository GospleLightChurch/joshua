/**
 * 后端留言页面
 * Created by y27chen on 2016/10/30.
 */
import AdminBasePage from "../../joshua-admin-base-page";
import Dialog from "../../component/dialog";

export default class MessageTablePage extends AdminBasePage {
    init() {
        let table = this;

        $(".ui.admin.mark.read.button").on("click", function () {
            table.asRead($(this).data("id"));
        });

        $(".ui.admin.mark.unread.button").on("click", function () {
            table.asUnRead($(this).data("id"));
        });
    }

    asRead(id) {
        var formData = new FormData();
        formData.append("_csrf", $(".ui.admin.user.form input[name='_csrf']").val());
        formData.append("id", id);

        $.ajax({
            url: "/message/read",
            type: "post",
            data: formData,
            processData: false,
            contentType: false,
            success: function (status) {
                if ("success" == status) {
                    window.location.reload();
                } else {
                    new Dialog("操作失败", "操作失败，原因：" + status, function () {
                    }).error();
                }
            },
            error: function () {
                new Dialog("操作失败", "操作失败", function () {
                }).error();
            }
        });
    }

    asUnRead(id) {
        var formData = new FormData();
        formData.append("_csrf", $(".ui.admin.user.form input[name='_csrf']").val());
        formData.append("id", id);

        $.ajax({
            url: "/message/unread",
            type: "post",
            data: formData,
            processData: false,
            contentType: false,
            success: function (status) {
                if ("success" == status) {
                    window.location.reload();
                } else {
                    new Dialog("操作失败", "操作失败，原因：" + status, function () {
                    }).error();
                }
            },
            error: function () {
                new Dialog("操作失败", "操作失败", function () {
                }).error();
            }
        });
    }
}

$(document).ready(()=> {
    var page = new MessageTablePage();
    page.init();
});
