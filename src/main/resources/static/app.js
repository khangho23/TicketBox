import "./styles/app.css";
import {JetApp} from "webix-jet";
import TopView from "./views/main";

export default class InventoryApp extends JetApp {
	constructor(config){
		super(webix.extend({
			start:		"/main/dash",
			view: { main: TopView }
		}, config, true));
	}
}