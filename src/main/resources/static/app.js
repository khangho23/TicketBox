import "//cdn.webix.com/libs/jet/webix-jet.js"
import Main from "./views/main.js";
import Dash from "./views/dash/index.js";
import Actor from "./views/management/actor/index.js"
import Director from "./views/management/director/index.js"
import Movie from "./views/management/movie/index.js"

const JetApp = webix.jet.JetApp;

export default class InventoryApp extends JetApp {
	constructor(config) {
		super(webix.extend({
			id: "adminApp",
			start: "/app/dash",
			views: {
				app: Main,
				dash: Dash,
				"management-movie": Movie,
				"management-director": Director,
				"management-actor": Actor,
			}
		}, config, true));
	}
}