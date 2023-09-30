import "//cdn.webix.com/libs/jet/webix-jet.js"
import MenuView from "./menu.js";

const JetView = webix.jet.JetView;

export default class TopView extends JetView {
	config() {
		const theme = this.app.config.theme;
		return {
			type: "clean",
			rows: [
				{
					view: "toolbar",
					css: theme,
					height: 60,
					elements: [
						{
							paddingY: 7,
							rows: [
								{
									cols: [
										{
											view: "icon",
											icon: "mdi mdi-menu",
											click: () => this.app.callEvent("menu:toggle")
										},
										{
											view: "label", label: "ZUHOT - Admin", css: "header_label"
										},
										{},
										{ width: 8 },
										{
											view: "icon",
											localId: "themes",
											icon: "mdi mdi-invert-colors",
											tooltip: theme ? "Come back to the light side of the Force" : "Come to the dark side",
											color: theme,
											click: function() {
												let color = this.config.color;
												color = !color ? "webix_dark" : "";
												try {
													webix.storage.local.put("theme_dash", color);
												}
												catch (err) {/* for blocked cookies */ }
												this.$scope.app.config.theme = color;
												this.$scope.app.refresh();
											},
										},
										{
											view: "icon",
											icon: "mdi mdi-logout",
											type: "icon",
											tooltip: "Logout",
											click: () => {
												webix.confirm({
													title: "Bạn có muốn đăng xuất",
													ok: "OK",
													cancel: "Cancel",
													type: "confirm-success"
												}).then(() =>
													setTimeout(() => window.location.href = "/logout", 2000)
												)
											}
										}
									]
								}
							]
						},
						{ width: 6 }
					]
				},
				{
					cols: [
						MenuView,
						{ $subview: true }
					]
				}
			]
		};
	}
}