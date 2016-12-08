/**
 * 后端文章审核页面
 * Created by y27chen on 2016/10/29.
 */
import AdminBasePage from "../../joshua-admin-base-page";
import Editor from "../../component/editor";
import Dialog from "../../component/dialog";

class AdminArticleAuditorPage extends AdminBasePage {
    constructor() {
        super();
        this.editor = new Editor();
    }

    init() {
        var editor = this;

        $(".article-audit .publish.button").on("click", function () {
            editor.publish();
        });

        $(".article-audit .reject.button").on("click", function () {
            editor.reject();
        });
    }

    publish() {
        let editor = this;
        let id = $("#article-reader").data("id");

        new Dialog("发布文章", "确定要发布文章？ 文章发布以后即可在网站访问", function () {
            editor.showDimmer();
            var formData = new FormData();
            formData.append("_csrf", $(".joshua.csrf input[name='_csrf']").val());
            formData.append("type", "publish");

            $.ajax({
                url: "/api/article/" + id,
                type: "post",
                data: formData,
                processData: false,
                contentType: false,
                success: function (response) {
                    if ("OK" == response.status) {
                        window.location.reload();
                    } else {
                        new Dialog("发布文章", "发布失败，原因:" + response.message, function () {
                            editor.hideDimmer();
                        }).error();
                    }
                },
                error: function () {
                    new Dialog("发布文章", "发布失败", function () {
                        editor.hideDimmer();
                    }).error();
                }
            });
        }).confirm();
    }

    reject() {
        let editor = this;
        let id = $("#article-reader").data("id");

        new Dialog("驳回文章", "确定要驳回文章？", function () {
            editor.showDimmer();
            var formData = new FormData();
            formData.append("_csrf", $(".joshua.csrf input[name='_csrf']").val());
            formData.append("type", "reject");

            $.ajax({
                url: "/api/article/" + id,
                type: "post",
                data: formData,
                processData: false,
                contentType: false,
                success: function (response) {
                    if ("OK" == response.status) {
                        window.location.reload();
                    } else {
                        new Dialog("驳回文章", "驳回失败，原因:" + response.message, function () {
                            editor.hideDimmer();
                        }).error();
                    }
                },
                error: function () {
                    new Dialog("驳回文章", "驳回失败", function () {
                        editor.hideDimmer();
                    }).error();
                }
            });
        }).confirm();
    }

    showDimmer() {
        $(".ui.dimmer").dimmer("show");
    }

    hideDimmer() {
        $(".ui.dimmer").dimmer("hide");
    }
}

$(document).ready(()=> {
    var page = new AdminArticleAuditorPage();
    page.init();
    page.editor.loadAsReader();
    page.editor.loadContent($("#article-reader").data("id"), page.editor.initContent);
});