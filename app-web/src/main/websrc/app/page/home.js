/**
 * 首页
 * Created by y27chen on 2016/10/29.
 */
import AnimationPage from "../joshua-animation-page";
import IdealImageSlider from "../lib/ideal-image-slider";
import "../lib/ideal-image-slider-bullet-nav";

class HomePage extends AnimationPage {
    loadSlider() {
        var slider = new IdealImageSlider.Slider({
            selector: '#slider',
            height: 450,
            interval: 4000,
            effect: 'fade'
        });
        slider.addBulletNav();
        slider.start();
    }
}

$(document).ready(() => {
    var page = new HomePage();
    page.loadSlider();
});