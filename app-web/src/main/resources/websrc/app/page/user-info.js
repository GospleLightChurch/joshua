/**
 * 个人信息页面
 * Created by y27chen on 2016/10/29.
 */

import BasePage from "../joshua-base-page";
import Dialog from "../component/dialog";

class UserInfoPage extends BasePage {
    init() {
        $(".ui.user.password.submit.button").on("click", ()=> {
            var newPassword = $("#new-password").val();
            var confirmPassword = $("#confirm-password").val();

            if (newPassword.length < 8) {
                new Dialog("修改密码", "新的密码长度不能少于8位").warning();
                return;
            }

            if (newPassword != confirmPassword) {
                new Dialog("修改密码", "两次密码输入不一致").warning();
                return;
            }

            var formData = new FormData();
            formData.append("_csrf", $(".ui.admin.user.form input[name='_csrf']").val());
            formData.append("password", newPassword);

            $.ajax({
                url: "/password",
                type: "post",
                data: formData,
                processData: false,
                contentType: false,
                success: function (status) {
                    if ("success" == status) {
                        new Dialog("修改密码", "修改密码成功", function () {
                            window.location.reload();
                        }).message();
                    } else {
                        new Dialog("修改密码", "修改密码失败，原因:" + status, function () {
                        }).error();
                    }
                },
                error: function () {
                    new Dialog("修改密码", "修改密码失败", function () {
                    }).error();
                }
            });
        });
    }
}

$(document).ready(() => {
    let page = new UserInfoPage();
    page.init();
});