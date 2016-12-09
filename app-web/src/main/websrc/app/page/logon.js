import BasePage from "../app-base";

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