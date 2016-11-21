/**
 * 文章中心页面
 * Created by y27chen on 2016/10/29.
 */

import BasePage from "../joshua-base-page";

class ArticleCenterPage extends BasePage {
    init() {
        $(".joshua.tabular .item").tab();
    }
}

$(document).ready(() => {
    let page = new ArticleCenterPage();
    page.init();
});