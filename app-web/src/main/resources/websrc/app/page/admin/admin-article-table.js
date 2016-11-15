/**
 * 后端文章列表页面
 * Created by y27chen on 2016/10/29.
 */
import AdminBasePage from "../../joshua-admin-base-page";
import Dialog from "../../component/dialog";

class AdminArticleTablePage extends AdminBasePage {
    init() {
        let articleTable = this;

        $(".ui.admin.article.enable.button").on("   click", function () {
            articleTable.enable($(this).data("id"));
        });

        $(".ui.admin.article.disable.button").on("click", function () {
            articleTable.disable($(this).data("id"));
        });
    }

    enable(id) {
        new Dialog("激活文章", "激活文章后会重新在网站显示，确认要激活吗？", function () {
            var formData = new FormData();
            formData.append("_csrf", $(".ui.admin.user.form input[name='_csrf']").val());
            formData.append("id", id);

            $.ajax({
                url: "/api/article/enable",
                type: "post",
                data: formData,
                processData: false,
                contentType: false,
                success: function (status) {
                    if ("success" == status) {
                        window.location.reload();
                    } else {
                        new Dialog("激活文章", "激活失败，原因：" + status, function () {
                        }).error();
                    }
                },
                error: function () {
                    new Dialog("激活文章", "激活失败", function () {
                    }).error();
                }
            });
        }).confirm();
    }

    disable(id) {
        new Dialog("禁用文章", "禁用文章后不会在网站显示，确认要禁用吗？", function () {
            var formData = new FormData();
            formData.append("_csrf", $(".ui.admin.user.form input[name='_csrf']").val());
            formData.append("id", id);

            $.ajax({
                url: "/api/article/disable",
                type: "post",
                data: formData,
                processData: false,
                contentType: false,
                success: function (status) {
                    if ("success" == status) {
                        window.location.reload();
                    } else {
                        new Dialog("禁用文章", "禁用失败，原因：" + status, function () {
                        }).error();
                    }
                },
                error: function () {
                    new Dialog("禁用文章", "禁用失败", function () {
                    }).error();
                }
            });
        }).confirm();
    }
}

$(document).ready(()=> {
    var page = new AdminArticleTablePage();
    page.init();
});