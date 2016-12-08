/**
 * 后端用户管理列表页面
 * Created by y27chen on 2016/10/30.
 */
import AdminBasePage from "../../joshua-admin-base-page";
import Dialog from "../../component/dialog";

export default class UserTablePage extends AdminBasePage {
    init() {
        let table = this;
        $(".ui.admin.user.lock").on("click", function () {
            table.perform("lock", $(this).text().trim(), $(this).data("username"));
        });

        $(".ui.admin.user.unlock").on("click", function () {
            table.perform("unlock", $(this).text().trim(), $(this).data("username"));
        });

        $(".ui.admin.user.disable").on("click", function () {
            table.perform("disable", $(this).text().trim(), $(this).data("username"));
        });

        $(".ui.admin.user.enable").on("click", function () {
            table.perform("enable", $(this).text().trim(), $(this).data("username"));
        });
    }

    perform(url, text, username) {
        new Dialog(text + "用户", "确定要" + text + "用户 " + username + " 吗？", function () {
            var formData = new FormData();
            formData.append("_csrf", $(".ui.admin.user.form input[name='_csrf']").val());
            $.ajax({
                url: "/api/user/" + url + "/" + username,
                type: "post",
                data: formData,
                processData: false,
                contentType: false,
                success: function (status) {
                    if (status) {
                        location.reload();
                    } else {
                        new Dialog("消息", text + "用户失败").error();
                    }
                },
                error: function () {
                    new Dialog("消息", text + "用户失败").error();
                }
            });
        }).confirm();
    }
}


$(document).ready(()=> {
    var page = new UserTablePage();
    page.init();
});
