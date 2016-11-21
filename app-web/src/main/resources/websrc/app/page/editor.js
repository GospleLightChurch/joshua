/**
 * 文章编辑页面
 * Created by y27chen on 2016/10/29.
 */

import BasePage from "../joshua-base-page";
import Editor from "../component/editor";
import Dialog from "../component/dialog";

class EditorPage extends BasePage {
    constructor() {
        super();
        this.editor = new Editor();
    }

    init() {
        var editor = this;
        $(".ui.save.article.button").on("click", function () {
            editor.saveArticle();
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

        $("#article-fellowship").dropdown();
    }

    saveArticle() {
        var editor = this;
        var quill = this.editor.editor;
        var id = $("#article-id").val();
        var title = $("#article-title").val();
        var fellowship = $("#article-fellowship").dropdown('get value');

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
        formData.append("_csrf", $(".joshua.csrf input[name='_csrf']").val());
        formData.append("id", id ? id : -1);
        formData.append("file", $("#file")[0].files[0]);
        formData.append("title", title);
        formData.append("fellowship", fellowship);
        formData.append("description", $("#article-desc").val());
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

    showDimmer() {
        $(".ui.dimmer").dimmer("show");
    }

    hideDimmer() {
        $(".ui.dimmer").dimmer("hide");
    }
}

$(document).ready(() => {
    var page = new EditorPage();
    page.init();
    page.editor.loadAsEditor();
    page.editor.loadContent($("#article-id").val(), page.editor.initContent);
});