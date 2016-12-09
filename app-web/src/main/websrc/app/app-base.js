import "../css/joshua-root.scss";
import "../semantic/dist/semantic.css";
import Navigation from "./component/navigation";

require("../node_modules/semantic-ui/dist/semantic");

export default class BasePage {
    constructor() {
        new Navigation();
    }
}
