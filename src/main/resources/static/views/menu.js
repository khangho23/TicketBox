import "//cdn.webix.com/libs/jet/webix-jet.js";

const JetView = webix.jet.JetView;
const plugins = webix.jet.plugins;

export default class MenuView extends JetView {
	config() {
		const theme = this.app.config.theme;
		return {
			width: 200,
			localId: "side:menu",
			view: "sidebar",
//			collapsed: true,
			css: theme,
			data: [
				{ id: "dash", value: "Dashboard", icon: "mdi mdi-view-dashboard" },
				{
					id: "management", icon: "mdi mdi-puzzle", value: "Quản lý", data: [
						{ id: "managementMovie", value: "Phim" },
						{ id: "managementActor", value: "Đạo diễn" },
						{ id: "managementDirector", value: "Diễn viên" }
					]
				},
				{ id: "statistical", value: "Thống kê", icon: "mdi mdi-chart-areaspline" },
				{ id: "setting", value: "Cài đặt", icon: "mdi mdi-cogs" },
				{ id: "help", value: "Hỗ trợ", icon: "wxi-alert" }
			]
		};
	}
	init(sidebar) {
		this.use(plugins.Menu, this.$$("side:menu"));
		this.on(this.app, "menu:toggle", () => this.$$("side:menu").toggle());
		sidebar.getPopup().attachEvent("onBeforeShow", () => false);
	}
}