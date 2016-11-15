/**
 * 前端文章页面
 * Created by y27chen on 2016/10/29.
 */

import BasePage from "../joshua-base-page";

class ContactPage extends BasePage {
    init() {
        $(".ui.fellowship.dropdown").dropdown();
        $(".ui.message.form .ui.radio.checkbox").checkbox();
    }
}

$(document).ready(() => {
    var page = new ContactPage();
    page.init();
});