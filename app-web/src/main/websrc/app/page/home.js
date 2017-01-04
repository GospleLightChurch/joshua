import "../../css/lib-ideal-image-slider.css";
import "../../css/lib-ideal-image-slider-default-theme.css";
import BaePage from "../app-front-base";
import IdealImageSlider from "../lib/ideal-image-slider";
import "../lib/ideal-image-slider-bullet-nav";

class HomePage extends BaePage {
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