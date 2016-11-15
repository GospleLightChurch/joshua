/**
 * 登录页面
 * Created by y27chen on 2016/10/29.
 */

import BasePage from "../joshua-base-page";

class LogonPage extends BasePage {
    init() {
        $("input.login").popup({
            on: "focus"
        });
    }
}
$(document).ready(() => {
    let page = new LogonPage();
    page.init();
});