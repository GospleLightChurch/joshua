/**
 * 后端首页管理页面
 * Created by y27chen on 2016/10/30.
 */
import AdminBasePage from "../../joshua-admin-base-page";
import Dialog from "../../component/dialog";
import "lightgallery";

export default class AdminStaticHomePage extends AdminBasePage {
    init() {

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

        $(".ui.add.slide.picture.button").on("click", function () {
            var link = $("#link").val();
            var file = $("#file")[0].files[0];

            if (!link) {
                new Dialog("添加大图", "请输入大图链接").message();

                return;
            }

            if (file) {
                var formData = new FormData();
                formData.append("_csrf", $(".ui.admin.user.form input[name='_csrf']").val());
                formData.append("file", file);
                formData.append("link", link);

                $.ajax({
                    url: "/api/gallery/static/add",
                    type: "post",
                    data: formData,
                    processData: false,
                    contentType: false,
                    success: function (status) {
                        if (status == "success") {
                            window.location.reload();
                        } else {
                            new Dialog("上传大图", "上传大图失败，原因：" + status, function () {
                            }).error();
                        }
                    },
                    error: function () {
                        new Dialog("上传大图", "上传大图失败", function () {
                        }).error();
                    }
                });
            } else {
                new Dialog("上传大图", "请选择要上传的图片").message();
            }
        });

        $(".ui.delete.slide.picture.button").on("click", function () {
            var id = $(this).data("id");

            var formData = new FormData();
            formData.append("_csrf", $(".ui.admin.user.form input[name='_csrf']").val());
            formData.append("id", id);

            $.ajax({
                url: "/api/gallery/static/remove",
                type: "post",
                data: formData,
                processData: false,
                contentType: false,
                success: function (status) {
                    if (status == "success") {
                        window.location.reload();
                    } else {
                        new Dialog("删除大图", "删除大图失败，原因：" + status, function () {
                        }).error();
                    }
                },
                error: function () {
                    new Dialog("删除大图", "删除大图失败", function () {
                    }).error();
                }
            });
        });

        $("#lightGallery").lightGallery();
    }
}

$(document).ready(()=> {
    var page = new AdminStaticHomePage();
    page.init();
});