import BasePage from "../app-front-base";

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