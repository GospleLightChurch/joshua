/**
 * 后端文章编辑页面
 * Created by y27chen on 2016/10/29.
 */
import AdminBasePage from "../../joshua-admin-base-page";
import Editor from "../../component/editor";
import Dialog from "../../component/dialog";

class AdminArticleEditorPage extends AdminBasePage {
    constructor() {
        super();
        this.editor = new Editor();
    }

    init() {
        var editor = this;
        $(".article-editor .ui.save.button").on("click", function () {
            editor.saveArticle();
        });

        $(".article-editor .ui.delete.button").on("click", function () {
            editor.deleteArticle();
        });

        $(".article-editor .ui.audit.button").on("click", function () {
            editor.auditArticle();
        });

        $(".joshua.cover.input").on("change", function (e) {
            var file = e.target.files[0];
            var imageType = /^image\//;
            if (!imageType.test(file.type)) {
                new Dialog("选择封面", "请选择正确的图片").warning();
                return;
            }

            if (window.FileReader) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    var img = document.getElementById("cover-picture");
                    img.src = e.target.result;
                };
                reader.readAsDataURL(file);
            } else {
                new Dialog("选择封面", "您的设部不支持该功能").error();
            }
        });

        $(".article-editor .ui.fellowship.dropdown").dropdown();
    }

    saveArticle() {
        var editor = this;
        var quill = this.editor.editor;
        var id = $("#article-id").val();
        var title = $(".article-editor .input.title").val();
        var fellowship = $(".article-editor .dropdown.fellowship").dropdown('get value');

        if (!title) {
            new Dialog("保存文章", "文章标题不能为空").message();
            return;
        }

        if (!fellowship) {
            new Dialog("保存文章", "文章所属团契不能为空").message();
            return;
        }

        if (quill.getLength() == 1) {
            new Dialog("保存文章", "文章内容不能为空").message();
            return;
        }

        editor.showDimmer();

        var formData = new FormData();
        formData.append("_csrf", $(".ui.admin.user.form input[name='_csrf']").val());
        formData.append("id", id ? id : -1);
        formData.append("file", $("#file")[0].files[0]);
        formData.append("title", title);
        formData.append("fellowship", fellowship);
        formData.append("description", $(".article-editor .input.description").val());
        formData.append("content",
            JSON.stringify(quill.getContents())
                .replace(/\n/g, "\\n")
        );

        $.ajax({
            url: "/api/article/save",
            type: "post",
            data: formData,
            processData: false,
            contentType: false,
            xhr: function () {
                var myXhr = $.ajaxSettings.xhr();

                if (myXhr.upload) {
                    myXhr.upload.addEventListener("progress", function (e) {
                        if (e.lengthComputable) {
                            console.log("上传数据：" + e.loaded + " - " + e.total);
                        }
                    }, false);
                }

                return myXhr;
            },
            success: function (status) {
                if (parseInt(status)) {
                    window.location = '/admin/article/' + status + '/edit';
                } else {
                    new Dialog("保存文章", "保存失败，原因：" + status, function () {
                        editor.hideDimmer();
                    }).error();
                }
            },
            error: function () {
                new Dialog("保存文章", "保存失败", function () {
                    editor.hideDimmer();
                }).error();
            }
        });
    }

    deleteArticle() {
        let id = $("#article-id").val();
        let formData = new FormData();
        formData.append("_csrf", $(".ui.admin.user.form input[name='_csrf']").val());
        formData.append("id", id);

        $.ajax({
            url: "/api/article/delete",
            type: "post",
            data: formData,
            processData: false,
            contentType: false,
            success: function (status) {
                if (status == "success") {
                    new Dialog("删除文章", "删除文章成功", function () {
                        window.location = '/admin/article';
                    }).message();
                } else {
                    new Dialog("删除文章", "删除文章失败，原因：" + status).error();
                }
            },
            error: function () {
                new Dialog("删除文章", "删除文章失败").error();
            }
        });
    }

    auditArticle() {
        let editor = this;
        new Dialog("申请发布", "确定要申请发布文章吗？， 申请发布后将不能再修改文章内容，如果文章被驳回，则可以修改以后继续发布", function () {
            editor.showDimmer();

            let formData = new FormData();
            formData.append("_csrf", $(".ui.admin.user.form input[name='_csrf']").val());
            formData.append("id", $("#article-id").val());

            $.ajax({
                url: "/api/article/audit",
                type: "post",
                data: formData,
                processData: false,
                contentType: false,
                success: function (status) {
                    if ("success" == status) {
                        window.location.reload();
                    } else {
                        new Dialog("申请发布", "申请发布失败，原因:" + status, function () {
                            editor.hideDimmer();
                        }).error();
                    }
                },
                error: function () {
                    new Dialog("申请发布", "申请发布失败", function () {
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
    var page = new AdminArticleEditorPage();
    page.init();
    page.editor.loadAsEditor();
    page.editor.loadContent($("#article-id").val(), page.editor.initContent);
});