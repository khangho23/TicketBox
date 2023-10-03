import "//cdn.webix.com/libs/jet/webix-jet.js"
import MovieService from "./service.js";
const JetView = webix.jet.JetView;

export default class Movie extends JetView {

	config() {

		return {
			view: "scrollview",
			scroll: "y",
			body: {
				rows: [{
					view: "form",
					id: "qlp",
					rows: [
						{
							view: "form",
							id: "Form",
							height: 1000,
							padding: 10,
							elements: [
								{
									cols: [{
										rows: [{
											view: "template",
											template: "#poster#",
											id: "poster",
											name: "poster",
											height: 200
										}, {
											view: "uploader",
											inputWidth: 130,
											value: "View an image",
											accept: "image/jpeg, image/png",
											autosend: false,
											multiple: false,
											on: {
												onBeforeFileAdd: function(upload) {
													var file = upload.file;
													var reader = new FileReader();
													reader.onload = function(event) {
														// console.log(event.target.result);
														$$("tmpWin").getBody().setValues({ src: event.target.result });
														$$("tmpWin").show()
														console.log(event.target.result)
													};
													reader.readAsDataURL(file)
													return false;
												}
											}
										}]
									}]
								},
								{
									cols: [{ view: "text", name: "id", label: "Mã Phim", labelWidth: 120 },
									{ view: "text", name: "name", label: "Tên Phim", labelWidth: 120 },
									{ view: "text", name: "yearofmanufacture", label: "Năm Khởi Chiếu", labelWidth: 120 },]
								},
								{
									cols: [{

										view: "select",
										label: "Quốc Gia",
										id: "country",
										name: "country",
										options: [],
										labelWidth: 120
									},
									{ view: "text", name: "time", label: "Thời Lượng", labelWidth: 120 },
									{

										view: "select",
										name: "status",
										id: "status",
										label: "Trạng Thái",
										options: [
											{ id: 0, value: "Sắp chiếu" },
											{ id: 1, value: "Đang chiếu" },
											{ id: 2, value: "Ngừng chiếu" }
										], labelWidth: 120
									},]
								},
								{ view: "text", name: "director", id: "director", label: "Đạo Diễn", labelWidth: 120 },
								{ view: "text", name: "actor", id: "actor", label: "Diễn Viên", labelWidth: 120 },
								{ view: "text", name: "trailer", label: "Trailer", labelWidth: 120 },
								{ view: "textarea", name: "describe", label: "Mô Tả", labelWidth: 120, height: 100 },
								{
									cols: [{
										rows: [
											{
												view: "text",
												label: "Ngôn Ngữ",
												id: "selectedLanguage",
												name: "language",
												readonly: true
											},
											{
												view: "list",
												id: "language",
												name: "language",
												select: false,
												data: [],
												height: 200,
												scrollX: false,
												footer: true,
												on: {
													onItemClick: function(id) {
														const item = this.getItem(id);
														item.selected = !item.selected;
														this.updateItem(id, item);
														const selectedItems = this.find(item => item.selected).map(item => item.value);
														$$("selectedLanguage").setValue(selectedItems.join(", "));
													}
												}
											}
										]
									}, {
										rows: [{
												view: "list",
												label: "Thể Loại",
												id: "type1",
												name:"type1",
												value:[],
												readonly: true
											},
											{
												view: "text",
												label: "Thể Loại",
												id: "selectedType",

												readonly: true
											},
											{
												view: "list",
												id: "type",
												name: 'type',
												select: false,
												data: [],
												height: 200,
												scrollX: false,
												footer: true,
												value:[],
												on: {
													onItemClick: function(id) {
														const item = this.getItem(id);
														item.selected = !item.selected;
														this.updateItem(id, item);
														const selectedItems = this.find(item => item.selected).map(item => item.value);
														const test = this.find(item => item.selected).map(item => { return { id: item.idType, name: item.value } });
														console.log(test)
														$$("selectedType").setValue(selectedItems.join(", "));
														$$("type1").define("value", test);
														$$("type1").refresh();
													}
												}
											}
										]
									}]
								}
								,
								{
									cols: [
										{ view: "button", value: "Thêm", css: "webix_primary", click: MovieService.insert },
										{ view: "button", value: "Xóa" },
										{ view: "button", value: "Sửa" },
										{ view: "button", value: "Mới" }
									]
								}
							]

						}]
				},
				{
					view: "datatable",
					id: "datatable",
					columns: [
						{ id: "id", header: ["Mã Phim", { content: "textFilter" }], fillspace: true },
						{ id: "name", header: ["Tên Phim", { content: "textFilter" }], fillspace: true },
						{ id: "yearofmanufacture", header: ["Năm Phát Hành", { content: "selectFilter" }], width: 140, css: { "text-align": "center" }, tooltip: "", editor: "text" },
						{ id: "time", header: ["Thời Lượng", { content: "selectFilter" }], width: 140, css: { "text-align": "center" }, tooltip: "", editor: "text" },
						{ id: "limitage", header: ["Độ Tuổi", { content: "selectFilter" }], width: 140, css: { "text-align": "center" }, tooltip: "", editor: "text" },
						{ id: "countryName", header: ["Quốc Gia", { content: "selectFilter" }], width: 140, css: { "text-align": "center" }, tooltip: "", editor: "text" },
						{ id: "poster", header: "Ảnh", width: 140, minHeight: 100, css: { "text-align": "center" }, tooltip: "", editor: "text", template: "<img src='/images/movie/#poster#' style='width:50px'/>" },

					],
					height: 300,
					scrollX: false,
					footer: true,
					fixedRowHeight: false, rowLineHeight: 60, rowHeight: 60,
					on: {
						onItemClick: function(id) {
							const selectedItem = this.getItem(id);
							if (selectedItem) {
								const movieId = selectedItem.id;
								MovieService.fillForm(movieId);
							}
						}
					}
				}
				]
			}
		}
	};
	init() {
		MovieService.fillMovie();
	}
}