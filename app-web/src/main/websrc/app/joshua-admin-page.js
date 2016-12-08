/**
 * 前端页面组件基类
 * Created by y27chen on 2016/10/29.
 */
import "./common";
import Navigation from "./component/admin-navigation";

export default class AdminBasePage {
    constructor() {
        new Navigation();
    }
}
