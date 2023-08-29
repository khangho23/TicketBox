import "./styles/app.css";
import {JetApp} from "https://cdn.jsdelivr.net/npm/webix-jet@3.0.1/dist/jet.umd.min.js";

export default class InventoryApp extends JetApp {
	constructor(config){
		super(webix.extend({
			start:		"/main/dash"
		}, config, true));
	}
}