/**
 * 后端文章预览页面
 * Created by y27chen on 2016/10/29.
 */
import AdminBasePage from "../../joshua-admin-base-page";
import Editor from "../../component/editor";

class AdminArticleReaderPage extends AdminBasePage {
    constructor() {
        super();
        this.editor = new Editor();
    }
}

$(document).ready(()=> {
    var page = new AdminArticleReaderPage();
    page.editor.loadAsReader();
    page.editor.loadContent($("#article-id").val(), page.editor.initContent);
});