// import {JetView} from "webix-jet";
import "//cdn.webix.com/libs/jet/webix-jet.js"
import { DateUtil } from "./../../../common/DateUtil.js";
const JetView = webix.jet.JetView;



export default class Room extends JetView {
	// init = async () => {
	// 	const { data: result } = await axios.get("/api/branch");
	// 	$$("list").parse(result);
	// }

	config() {
		function custom_checkbox(obj, common, value) {
			if (value == 0)
				return "<span class='badge text-bg-success'>Đang chiếu</span>";
			else if (value == 1)
				return "<span class='badge text-bg-primary'>Chưa chiếu</span>"
			else
				return "<span class='badge text-bg-danger'>Hết chiếu</span>";
		}
		return {
			height: 500,
			rows: [
				{
					cols: [
						{
							view: "combo",
							id: "comboBranch",
							label: "Chọn chi nhánh",
							inputWidth: 300,
							labelWidth: 150,
							value:"cn1",
							options: {
								view: "suggest", // optional
								body: { // list configuration
									view: "list", // optional
									id: "list",
									data: [],
									template: "#name#",
									yCount: 7
								}
							},
							on:{
								onAfterRender:async function (){
									const { data: result } = await axios.get("/api/branch");
									$$("list").parse(result);
								}
							},
							icon:"mdi mdi-arrow-down"
						},
						{
							view: "datepicker",
							label: "Chọn ngày chiếu",
							id:"datepick",
							inputWidth: 300,
							labelWidth: 150,
							value:"2023-8-31",
							timepicker: true,
							format: "%d/%m/%Y",
							on: {
								onchange: async function (newValue, oldValue) {
									
									const datepick = DateUtil.formatDate(newValue || oldValue);
									const { data: result } = await axios.get(`/api/room/getRoomWithFilm?id=${$$("comboBranch").getValue()}&showdate=${datepick}`);
									// console.log(result);
									$$("table-room").clearAll();
									$$("table-room").parse(result);
								},
								onAfterRender:async function (){
									const datepick = DateUtil.formatDate(this.getValue());
									const { data: result } = await axios.get(`/api/room/getRoomWithFilm?id=${$$("comboBranch").getValue()}&showdate=${datepick}`);
									$$("table-room").clearAll();
									$$("table-room").parse(result);
								}
							},
							icon:"mdi mdi-arrow-down"
						},
						{
							view: "",
							fillspace: 3
						}
					],
					css: {
						"margin": "20px 50px !important"
					}
				},
				{
					view: "datatable",
					id: "table-room",
					columns: [
						{ id: "id", header: "ID Phòng", fillspace: true },
						{ id: "name", editor: "text", header: "Tên phòng", fillspace: true },
						{ id: "moviename", editor: "popup", header: "Phim", fillspace: true },
						{ id: "starttime", editor: "popup", header: "Giờ chiếu", fillspace: true },
						{ id: "total", editor: "popup", header: "Số lượng", fillspace: true },
						{ id: "status", editor: "popup", header: "Trạng thái", fillspace: true, template: custom_checkbox }
					],
					select: true,
					autoheight: true,
					data: [],
					scrollX: false
				}
			]
		};
	}
}
