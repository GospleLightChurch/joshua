/**
 * 前端文章页面
 * Created by y27chen on 2016/10/29.
 */

import BasePage from "../joshua-base-page";
import Editor from "../component/editor";
import "../lib/qrcode";

class ArticlePage extends BasePage {
    constructor() {
        super();
        this.editor = new Editor();
    }

    initQRCode() {
        $("#qr-address").qrcode({
            width: 196,
            height: 196,
            text: encodeURI(window.location.href)
        });

        $('.ui.qr.icon').popup({
            position: "bottom center",
            inline: true,
            on: "click"
        });
    }
}

$(document).ready(() => {
    var page = new ArticlePage();
    page.editor.loadAsReader();
    page.editor.loadContent($("#article-id").val(), page.editor.initContent);
    page.initQRCode();
});