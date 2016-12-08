/**
 * 动画页面组件基类
 * Created by y27chen on 2016/10/29.
 */
import BasePage from "./joshua-base-page";
import AOS from "aos";

export default class AnimationPage extends BasePage {
    constructor() {
        super();
        AOS.init();
    }
}
