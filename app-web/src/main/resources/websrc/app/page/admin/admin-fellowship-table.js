/**
 * 后端团契列表页面
 * Created by y27chen on 2016/10/30.
 */
import AdminBasePage from "../../joshua-admin-base-page";
import Dialog from "../../component/dialog";

export default class FellowshipTablePage extends AdminBasePage{
    init(){
        let table = this;

        $(".ui.admin.fellowship.enable.button").on("click", function () {
            table.enable($(this).data("name"));
        });

        $(".ui.admin.fellowship.disable.button").on("click", function () {
            table.disable($(this).data("name"));
        });
    }

    enable(name) {
        new Dialog("激活团契", "激活团契后团契和团契所属的文章会重新在网站显示，确认要激活吗？", function () {
            var formData = new FormData();
            formData.append("_csrf", $(".ui.admin.user.form input[name='_csrf']").val());
            formData.append("name", name);

            $.ajax({
                url: "/api/fellowship/enable",
                type: "post",
                data: formData,
                processData: false,
                contentType: false,
                success: function (status) {
                    if ("success" == status) {
                        window.location.reload();
                    } else {
                        new Dialog("激活团契", "激活失败，原因：" + status, function () {
                        }).error();
                    }
                },
                error: function () {
                    new Dialog("激活团契", "激活失败", function () {
                    }).error();
                }
            });
        }).confirm();
    }

    disable(name) {
        new Dialog("禁用团契", "禁用团契后团契和团契所属的文章将不会在网站显示，确认要禁用吗？", function () {
            var formData = new FormData();
            formData.append("_csrf", $(".ui.admin.user.form input[name='_csrf']").val());
            formData.append("name", name);

            $.ajax({
                url: "/api/fellowship/disable",
                type: "post",
                data: formData,
                processData: false,
                contentType: false,
                success: function (status) {
                    if ("success" == status) {
                        window.location.reload();
                    } else {
                        new Dialog("禁用团契", "禁用失败，原因：" + status, function () {
                        }).error();
                    }
                },
                error: function () {
                    new Dialog("禁用团契", "禁用失败", function () {
                    }).error();
                }
            });
        }).confirm();
    }
}

$(document).ready(()=>{
   var page = new FellowshipTablePage();
    page.init();
});
