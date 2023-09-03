import "//cdn.webix.com/libs/jet/webix-jet.js"
import TopView from "./views/main.js";

const JetApp = webix.jet.JetApp;

export default class InventoryApp extends JetApp {
	constructor(config) {
		super(webix.extend({
			id: "adminApp",
			start: "/main/dash",
			views: {
				main: TopView
			}
		}, config, true));
	}
}