/**
 * 文章编辑器组件
 * Created by y27chen on 2016/10/29.
 */
import Quill from "../lib/quill";
import "../lib/quill-core";
import "lightgallery";

const EDITOR_CONFIG = {
    PLACEHOLDER: "请输入文章内容，文章大小最多为20M，超过以后会保存失败",
    TOOLBAR: [
        /* 基本控件 */
        [{'header': [1, 2, 3, 4, 5, 6, false]}],

        /* 文字样式控件 */
        ['bold', 'italic', 'underline', 'strike'],
        [{'color': []}, {'background': []}],

        /* 文字对齐控件 */
        [{'align': []}],
        [{'list': 'ordered'}, {'list': 'bullet'}],
        [{'indent': '-1'}, {'indent': '+1'}],

        /* 其他控件 */
        ['link', 'image'],
        [{'script': 'sub'}, {'script': 'super'}],
        ['clean']
    ],
    THEME: "snow"
};

export default class Editor {
    constructor() {
        this.editor = null;
    }

    loadAsEditor() {
        this.editor = new Quill(".article-editor .container", {
            placeholder: EDITOR_CONFIG.PLACEHOLDER,
            modules: {
                toolbar: EDITOR_CONFIG.TOOLBAR
            },
            theme: EDITOR_CONFIG.THEME
        });
    }

    loadAsReader() {
        this.editor = new Quill(".article-reader .container", {
            modules: {
                toolbar: false
            },
            theme: EDITOR_CONFIG.THEME,
            readOnly: true
        });
    }

    loadAsAuditor() {
        this.editor = new Quill(".article-audit .container", {
            modules: {
                toolbar: false
            },
            theme: EDITOR_CONFIG.THEME,
            readOnly: true
        });
    }

    loadContent(id, onSuccess) {
        let editor = this.editor;
        if (id) {
            $.ajax({
                url: "/article/content/" + id,
                type: "get",
                processData: false,
                contentType: false,
                success: (content) => {
                    if (content) {
                        var articleContent = JSON.parse(content);
                        editor.setContents(articleContent);

                        if (onSuccess) {
                            onSuccess.apply(editor);
                        }
                    }
                }
            });
        }
    }

    initContent() {
        var reader = $(".article-reader");

        reader.find("img").each(function () {
            var imgSrc = $(this).attr("src");
            $(this).attr("data-src", imgSrc);
        });

        reader.lightGallery({
            selector: 'img'
        });

        reader.removeAttr("style");
        $(".article-reader + .ui.dimmer").removeClass("active");
    }
}
