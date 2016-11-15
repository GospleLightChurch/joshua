/**
 * 后端团契详细信息页面
 * Created by y27chen on 2016/10/30.
 */
import AdminBasePage from "../../joshua-admin-base-page";
import Dialog from "../../component/dialog";

export default class FellowshipPage extends AdminBasePage {
    init() {
        let fellowship = this;

        $(".ui.admin.fellowship.remove.button").on("click", function () {
            fellowship.removeAdmin($("#fellowship-id").text().trim(), $(this).data("username"));
        });

        $(".ui.admin.fellowship.transfer.owner.button").on("click", function () {
            $("#owner-username").val("");
            $(".ui.admin.fellowship.transfer.modal").modal("show");
        });

        $(".ui.admin.fellowship.add.button").on("click", function () {
            $("#admin-username").val("");
            $(".ui.admin.fellowship.add.modal").modal("show");
        });

        $(".ui.admin.fellowship.transfer.modal").modal({
            closeable: false,
            onApprove: function () {
                var input = $("#owner-username");
                fellowship.transfer($("#fellowship-id").text().trim(), input.val());
            },
            onDeny: function () {
                return true;
            }
        });

        $(".ui.admin.fellowship.add.modal").modal({
            closeable: false,
            onApprove: function () {
                var input = $("#admin-username");
                fellowship.addAdmin($("#fellowship-id").text().trim(), input.val());
            },
            onDeny: function () {
                return true;
            }
        });
    }

    transfer(name, username) {
        var formData = new FormData();
        formData.append("_csrf", $(".ui.admin.user.form input[name='_csrf']").val());
        formData.append("name", name);
        formData.append("username", username);

        $.ajax({
            url: "/api/fellowship/transfer",
            type: "post",
            data: formData,
            processData: false,
            contentType: false,
            success: function (status) {
                if ("success" == status) {
                    window.location.reload();
                } else {
                    new Dialog("转移团契", "转移失败，原因：" + status, function () {
                    }).error();
                }
            },
            error: function () {
                new Dialog("转移团契", "转移失败", function () {
                }).error();
            }
        });
    }

    addAdmin(name, username) {
        var formData = new FormData();
        formData.append("_csrf", $(".ui.admin.user.form input[name='_csrf']").val());
        formData.append("name", name);
        formData.append("username", username);

        $.ajax({
            url: "/api/fellowship/add",
            type: "post",
            data: formData,
            processData: false,
            contentType: false,
            success: function (status) {
                if ("success" == status) {
                    window.location.reload();
                } else {
                    new Dialog("添加管理员", "添加管理员失败，原因：" + status, function () {
                    }).error();
                }
            },
            error: function () {
                new Dialog("添加管理员", "添加管理员失败", function () {
                }).error();
            }
        });
    }

    removeAdmin(name, username) {
        new Dialog("移除管理员", "确定要移除管理员" + username + "?", function () {
            var formData = new FormData();
            formData.append("_csrf", $(".ui.admin.user.form input[name='_csrf']").val());
            formData.append("name", name);
            formData.append("username", username);

            $.ajax({
                url: "/api/fellowship/remove",
                type: "post",
                data: formData,
                processData: false,
                contentType: false,
                success: function (status) {
                    if ("success" == status) {
                        window.location.reload();
                    } else {
                        new Dialog("移除管理员", "移除管理员失败，原因：" + status, function () {
                        }).error();
                    }
                },
                error: function () {
                    new Dialog("移除管理员", "移除管理员失败", function () {
                    }).error();
                }
            });
        }).confirm();
    }
}

$(document).ready(()=> {
    var page = new FellowshipPage();
    page.init();
});
