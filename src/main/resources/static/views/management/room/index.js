// import {JetView} from "webix-jet";
import "//cdn.webix.com/libs/jet/webix-jet.js"

const JetView = webix.jet.JetView;

export default class TopView extends JetView {

	config() {
		return {
			type: "space", rows: [
				{
					view: "datatable",
					columns: [
						{ id: "id", header: "", width: 30 },
						{ id: "title", editor: "text", header: "Tên phòng", width: 200 },
						{ id: "start", editor: "popup", header: "Chi nhánh", width: 120 },
						{ id: "end", editor: "popup", header: "Tình hình chỗ ngồi", width: 120 }
					],
					select: true,
					autoheight: true,
					autowidth: true,
					data: [
						{ id: 1, title: "The Shawshank Redemption", start: "Some pretty long text here and there, here and there", end: "Short text" },
						{ id: 2, title: "The Godfather", start: "", end: "" }
					]
				}
			]
		};
	}
}
