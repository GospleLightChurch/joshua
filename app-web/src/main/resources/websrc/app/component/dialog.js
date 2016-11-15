/**
 * Dialog Utils
 * Created by y27chen on 2016/1/27.
 */
"use strict";
var Dialog = function (title, content, onApprove, onDeny, predefined) {
    this.type = "m";
    this.title = title || "标题";
    this.content = content || "没有内容";
    this.onApprove = onApprove;
    this.onDeny = onDeny;
    this.predifined = predefined || false;

    this.modal = null;
};

Dialog.prototype.init = function () {
    $(".dialog").remove();
    /* create modal container */
    var modal = document.createElement("div");
    modal.setAttribute("class", "ui small modal dialog");

    /* create modal header */
    var header = document.createElement("div");
    header.setAttribute("class", "header");

    /* create modal message */
    var msgContent = document.createElement("div");
    msgContent.setAttribute("class", "content");

    /* for predefined only */
    var msgPreContainer = document.createElement("pre");

    /* create modal icon */
    var type = this.type;
    var headIcon = document.createElement("i");
    if (type == "w") {
        headIcon.setAttribute("class", "warning circle orange icon");
    } else if (type == "e") {
        headIcon.setAttribute("class", "warning sign red icon");
    } else if (type == "c") {
        headIcon.setAttribute("class", "help circle blue icon");
    } else {
        headIcon.setAttribute("class", "info circle green icon");
    }

    /* set modal structure */
    header.appendChild(headIcon);
    header.innerHTML = header.innerHTML + " " + this.title;

    if (this.predifined) {
        msgPreContainer.innerHTML = this.content;
        msgContent.appendChild(msgPreContainer);
    } else {
        msgContent.innerHTML = this.content;
    }

    modal.appendChild(header);
    modal.appendChild(msgContent);

    /* create action ui if needed */
    if (this.onApprove !== undefined) {
        /* create action container */
        var actions = document.createElement("div");
        actions.setAttribute("class", "actions");

        /* create positive buttons */
        var approve = document.createElement("div");
        approve.setAttribute("class", "ui positive button");
        approve.innerHTML = "确定";

        /* create*/
        var deny = document.createElement("div");
        deny.setAttribute("class", "ui negative button");
        deny.innerHTML = "取消";

        /* set modal structure */

        /* append deny button if defined deny callback */
        if (type == "c") {
            actions.appendChild(deny);
        }
        actions.appendChild(approve);
        modal.appendChild(actions);

        $(modal).modal({
            closable: false,
            onDeny: this.onDeny || function () {
            },
            onApprove: this.onApprove || function () {
            },
            blurring: true
        });
    }

    this.modal = $(modal);
};

Dialog.prototype.message = function () {
    this.type = "m";
    this.init();
    this.modal.modal("show");
};

Dialog.prototype.warning = function () {
    this.type = "w";
    this.init();
    this.modal.modal("show");
};

Dialog.prototype.error = function () {
    this.type = "e";
    this.init();
    this.modal.modal("show");
};

Dialog.prototype.confirm = function () {
    this.type = "c";
    this.init();
    this.modal.modal("show");
};

module.exports = Dialog;