import "//cdn.webix.com/libs/jet/webix-jet.js"

const JetView = webix.jet.JetView;
const plugins = webix.jet.plugins;

export default class TopView extends JetView {
    config() {
        const header = {
            type: "header", css: "custom_dark", height: 58,
            template: "ADMIN APP"
        };

        const sidebar = {
            localId: "menu",
            view: "sidebar", css: "webix_dark", width: 200,
            data: [
                { id: "dash", value: "Dashboard", icon: "mdi mdi-view-dashboard" },
                {
					id: "management", icon: "mdi mdi-puzzle", value: "Quản lý", data: [
						{ id: "management-movie", value: "Phim" },
						{ id: "management-actor", value: "Đạo diễn" },
						{ id: "management-director", value: "Diễn viên" }
					]
				},
                { id: "statistical", value: "Thống kê", icon: "mdi mdi-chart-areaspline" },
                { id: "setting", value: "Cài đặt", icon: "mdi mdi-cogs" },
                { id: "help", value: "Hỗ trợ", icon: "wxi-alert" }
            ]
        };

        const toolbar = {
            view: "toolbar",
            padding: 9, height: 58,
            cols: [
                { css: "logo" },
                { view: "icon", icon: "mdi mdi-bell", badge: "5" },
                { view: "icon", icon: "mdi mdi-settings" },
                {
                    template: `<image class="mainphoto" src="data/images/morgan_yu.jpg">
            <span class="webix_icon mdi mdi-circle status green"></span>`,
                    width: 60, css: "avatar", borderless: true
                }
            ]
        };

        return {
            type: "clean", cols: [
                { rows: [header, sidebar] },
                { rows: [toolbar, { $subview: true }] }
            ]
        };
    }

    init() {
        this.use(plugins.Menu, "menu");
    }
}