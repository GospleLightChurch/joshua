import BasePage from "../app-base";
import Editor from "../component/editor";
import Dialog from "../component/dialog";

class ArticleCenterPage extends BasePage {
    constructor() {
        super();
        this.editor = new Editor();
    }

    init() {
        let page = this;
        $(".ui.publish.article.button").on("click", function () {
            let id = $(this).data("id");
            page.publishArticle(id);
        });

        $(".ui.delete.article.button").on("click", function () {
            let id = $(this).data("id");
            page.deleteArticle(id);
        })
    }

    publishArticle(id) {
        new Dialog("申请发布", "确定要申请发布文章吗？， 申请发布后将不能再修改文章内容，如果文章被驳回，则可以修改以后继续发布", function () {
            let formData = new FormData();
            formData.append("_csrf", $(".joshua.csrf input[name='_csrf']").val());
            formData.append("type", "audit");

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
                        new Dialog("申请发布", "申请发布失败，原因:" + response.message, function () {
                        }).error();
                    }
                },
                error: function () {
                    new Dialog("申请发布", "申请发布失败", function () {
                    }).error();
                }
            });
        }).confirm();
    }

    deleteArticle(id) {
        new Dialog("删除文章", "确定要删除文章吗？", ()=> {
            let formData = new FormData();
            formData.append("_csrf", $(".joshua.csrf input[name='_csrf']").val());
            formData.append("type", "delete");

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
                        new Dialog("删除文章", "删除文章失败，原因：" + response.message).error();
                    }
                },
                error: function () {
                    new Dialog("删除文章", "删除文章失败").error();
                }
            });
        }).confirm();
    }
}

$(document).ready(() => {
    let page = new ArticleCenterPage();
    page.init();
});