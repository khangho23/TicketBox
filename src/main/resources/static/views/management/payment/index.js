import "//cdn.webix.com/libs/jet/webix-jet.js"
import PaymentService from "./service.js";
const JetView = webix.jet.JetView;

export default class Payment extends JetView {
	config() {
		const table = {
			view: "form",
			id: "table",
			borderless: true,
			height: 550,
			rows: [{
				view: "datatable",
				id: "datatable",
				css: "datatable",
				columns: [
					{ id: "branchName", header: ["Chi nhánh", { content: "selectFilter" }], width: 140, css: { "text-align": "center" }, tooltip: "", editor: "text" },
					{ id: "branchName", header: ["Phòng"], width: 140, css: { "text-align": "center" }, tooltip: "", editor: "text" },
					{ id: "branchName", header: ["Số lượng"], width: 140, css: { "text-align": "center" }, tooltip: "", editor: "text" },
				],
				height: 450,
				scrollX: false,
				footer: true,
				fixedRowHeight: false, rowLineHeight: 60, rowHeight: 60,
				on: {
					onItemClick: function(id) {
						const selectedItem = this.getItem(id);
					}
				}
			}]
		};

		return {
			view: "scrollview",
			scroll: "y",
			body: {
				rows: [table]
			}
		}
	};

	init() {
		PaymentService.init();
	}
}