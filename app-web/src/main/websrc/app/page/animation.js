import BasePage from "../app-front-base";
import AOS from "aos";

$(document).ready(() => {
    new BasePage();
    AOS.init();
});