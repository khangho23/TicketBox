import "//cdn.webix.com/libs/jet/webix-jet.js";

const JetView = webix.jet.JetView;
const plugins = webix.jet.plugins;

export default class MenuView extends JetView {
	config() {
		const theme = this.app.config.theme;
		return {
			width: 200,
			height: window.innerHeight,
			localId: "side:menu",
			view: "sidebar",
			//			collapsed: true,
			css: theme,
			data: [
				{ id: "dash", value: "Dashboard", icon: "mdi mdi-chart-bar-stacked" },
				{
					id: "management", icon: "mdi mdi-puzzle", value: "Quản lý Phim", data: [
						{ id: "managementMovie", value: "Phim", icon: "mdi mdi-movie" },

					]
				},
				{ id: "management-showtime", value: "Xuất chiếu", icon: "mdi mdi-movie-roll" },
				{ id: "setting", value: "Cài đặt", icon: "mdi mdi-cogs" },
				{ id: "help", value: "Hỗ trợ", icon: "wxi-alert" }
			]
		};
	}
	init(sidebar) {
		this.use(plugins.Menu, this.$$("side:menu"));
		$$(this.getUrl()[1].page).show();
		this.on(this.app, "menu:toggle", () => this.$$("side:menu").toggle());
		sidebar.getPopup().attachEvent("onBeforeShow", () => false);
	}
}