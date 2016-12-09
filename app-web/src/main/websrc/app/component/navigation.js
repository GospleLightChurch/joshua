/**
 * 导航栏模块
 * Created by y27chen on 2016/10/29.
 */
import Dialog from "./dialog";

export default class Navigation {
    constructor() {
        this.init();
    }

    init() {
        $('.ui.menu .ui.dropdown').dropdown({on: 'hover'});

        $('.ui.username.button').popup({
            position: "bottom center",
            inline: true,
            on: "click"
        });

        $(".joshua.menu.open").on("click", function () {
            $(".joshua.mobile.menu").toggle();
        });

        $('.ui.user.logout').on("click", this.logout);
    }

    logout() {
        new Dialog("注销", "您确定要注销吗？", function () {
            var formData = new FormData();
            formData.append("_csrf", $(".ui.user.logout form input[name='_csrf']").val());

            $.ajax({
                url: "/logout",
                type: "post",
                data: formData,
                processData: false,
                contentType: false,
                success: function () {
                    window.location = '/login?logout';
                },
                error: function () {
                    new Dialog("注销", "注销失败").error();
                }
            });
        }).confirm();
    }
}
