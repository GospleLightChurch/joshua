/**
 * 前端页面组件基类
 * Created by y27chen on 2016/10/29.
 */
import "./common";
import Navigation from "./component/navigation";

export default class BasePage {
    constructor() {
        new Navigation();
    }
}
