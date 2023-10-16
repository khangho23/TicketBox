import "//cdn.webix.com/libs/jet/webix-jet.js"
import MovieService from "./service.js";
const JetView = webix.jet.JetView;

export default class Movie extends JetView {

	config() {
		const form = {
			view: "form",
			id: "qlp",
			rows: [
				{
					view: "form",
					id: "Form",
					autoheight: true,
					padding: 10,
					css: "form",
					elements: [
						{
							cols: [
								{ view: "text", name: "id", id: "id", label: "<i class='fa-solid fa-barcode'></i> Mã Phim", invalidMessage: "Mã phim trống", labelWidth: 120, width: 100 },
								{ view: "text", name: "name", id: "name", label: "<i class='fa-solid fa-film'></i> Tên Phim", invalidMessage: "Tên phim trống", labelWidth: 120 },
								{ view: "text", name: "trailer", label: "<i class='fa-solid fa-trailer'></i> Trailer", invalidMessage: "Trailer trống", labelWidth: 120 },
							]
						},
						{
							cols: [
								{ view: "text", name: "yearofmanufacture", id: "yearofmanufacture", label: "<i class='fa-solid fa-calendar-days'></i> Năm Khởi Chiếu", invalidMessage: "Năm khởi chiếu trống", labelWidth: 120 },
								{
									view: "richselect",
									label: "<i class='fa-solid fa-earth-americas'></i> Quốc Gia",
									id: "country",
									value: 1,
									name: "country",
									options: [],
									labelWidth: 120
								}, {
									view: "richselect",
									name: "status",
									id: "status",
									value: 1,
									label: "<i class='fa-solid fa-gears'></i> Trạng Thái",
									options: [
										{ id: 0, value: "Sắp chiếu" },
										{ id: 1, value: "Đang chiếu" },
										{ id: 2, value: "Ngừng chiếu" }
									],
									labelWidth: 120
								},
								{ view: "text", name: "time", label: "<i class='fa-solid fa-clock'></i> Thời Lượng", invalidMessage: "Thời lượng trống", labelWidth: 120, width: 120 },
								{ view: "label", name: "label", label: "Phút", labelWidth: 30, width: 35, css: "label" },
							]
						},
						{
							cols: [
								{ view: "text", name: "director", id: "director", label: "<i class='fa-solid fa-user-pen'></i> Đạo Diễn", invalidMessage: "Đạo diễn trống", labelWidth: 120 },
								{ view: "text", name: "actor", id: "actor", label: "<i class='fa-solid fa-user-group '></i> Diễn Viên", invalidMessage: "Diễn viên trống", labelWidth: 120 }
							]
						},
						{ view: "textarea", name: "describe", label: "<i class='fa-solid fa-comment-medical'></i> Mô Tả", labelWidth: 120, invalidMessage: "Mô tả trống", css: "textarea", height: 90 }
						,
						{
							cols: [{
								rows: [
									{
										view: "text",
										label: "<i class='fa-solid fa-language '></i> Ngôn Ngữ <i style='font-style: italic;font-size: medium;font-family:serif;'>(Chỉ được thêm ngôn ngữ)</i>",
										id: "selectedLanguage",
										name: "selectedLanguage",
										readonly: true,
										invalidMessage: "Ngôn ngữ trống",
									},
									{
										view: "list",
										id: "language",
										select: false,
										data: [],
										height: 110,
										scrollX: false,
										footer: true,
										on: {
											onItemClick: async function (id) {
												let data = await MovieService.fillDataLanguage($$("Form").getValues().id);
												const item = this.getItem(id);
												let found = false;

												for (let i = 0; i < data.length; i++) {
													if (item.id == data[i].languageId) {
														found = true;
														break;
													}
												}

												item.selected = found ? true : !item.selected;
												this.updateItem(id, item);
												const itemNode = this.getItemNode(id);

												if (item.selected) {
													itemNode.classList.add("selected-item");
													itemNode.style.backgroundColor = "#C6E2FF";
												} else {
													itemNode.classList.remove("selected-item");
													itemNode.style.backgroundColor = "";
												}


												const selectedItems = this.find(item => item.selected).map(item => item.value);

												$$("selectedLanguage").setValue(selectedItems.join(", "));
											}
										}
									}
								]
							}, {
								rows: [
									{
										view: "text",
										label: "<i class='fa-solid fa-rectangle-list '></i> Thể Loại",
										id: "selectedType",
										name: "selectedType",
										readonly: true,
										invalidMessage: "Thể loại trống",
									},
									{
										view: "list",
										id: "type",
										select: false,
										data: [],
										height: 110,
										scrollX: false,
										footer: true,
										value: [],
										on: {
											onItemClick: function (id) {
												const item = this.getItem(id);
												item.selected = !item.selected;
												this.updateItem(id, item);
												const itemNode = this.getItemNode(id);
												if (item.selected) {
													itemNode.classList.add("selected-item");
													itemNode.style.backgroundColor = "#C6E2FF";
												} else {
													itemNode.classList.remove("selected-item");
												}
												const selectedItems = this.find(item => item.selected).map(item => item.value);
												$$("selectedType").setValue(selectedItems.join(", "));
											}
										}
									}
								]
							}
							]
						}, {
							cols: [{
								rows: [{
									view: "template",
									template: "#poster#",
									id: "poster",
									name: "poster",
									autoheight: true,
									css: "poster1",
									borderless: true
								}]
							}, {
								view: "template",
								template: "#src#",
								id: "trailer",
								name: "trailer",
								autoheight: true,
								borderless: true
							},]

						}, {
							cols: [
								{
									view: "uploader",
									id: "upload",
									name: "upload",
									value: "Chọn ảnh",
									accept: "image/jpeg, image/png, image/jpg",
									autosend: false,
									multiple: true,
									on: {
										onBeforeFileAdd: async function (upload) {
											var file = upload.file;
											var reader = new FileReader();
											reader.onload = async function (event) {
												$$("poster").define("data", {
													poster: "<img src='" + event.target.result + "' style='height:347px;width:290px' />",
													url: event.target.result,
													file: file
												});
											};
											reader.readAsDataURL(file)
											return false;
										}
									}, css: "upload"
								},
								{
									view: "button", value: "Thêm", css: "insert", click: function () {
										var form = $$("Form");
										if (form && form.validate()) {
											MovieService.insert();
										}
									}
								},
								{
									view: "button", value: "Cập nhật", css: "update", click: function () {
										var form = $$("Form");
										if (form && form.validate()) {
											MovieService.update();
										}
									}
								},
								{
									view: "button", value: "Mới", css: "new", click: MovieService.clear
								}
							]
						}
					],
					rules: {
						"id": webix.rules.isNotEmpty,
						"name": webix.rules.isNotEmpty,
						"yearofmanufacture": webix.rules.isNotEmpty,
						"country": webix.rules.isNotEmpty,
						"time": webix.rules.isNotEmpty,
						"status": webix.rules.isNotEmpty,
						"director": webix.rules.isNotEmpty,
						"actor": webix.rules.isNotEmpty,
						"trailer": webix.rules.isNotEmpty,
						"describe": webix.rules.isNotEmpty,
						"selectedType": webix.rules.isNotEmpty,
						"selectedLanguage": webix.rules.isNotEmpty,
						"upload": function (value) {
							// Lấy danh sách các files đã tải lên
							const uploadedFiles = $$("upload").files.data.pull;

							// Kiểm tra xem đã có file nào chưa
							if (Object.keys(uploadedFiles).length === 0) {
								webix.message("File ảnh trống.", "error");
								return false;
							}
							return true;
						}
					},
					elementsConfig: {
						labelPosition: "top",
						bottomPadding: 15
					}, on: {
						onAfterValidation: function (isValid) {
							if (!isValid) {
								// Form có lỗi, xử lý hiển thị lỗi ở đây
								webix.message("Dữ liệu không hợp lệ. Vui lòng kiểm tra lại.", "error");
							}
						}
					}

				}]
		};
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
					{ id: "id", header: ["Mã Phim", { content: "textFilter" }], fillspace: false, width: 90 },
					{ id: "name", header: ["Tên Phim", { content: "textFilter" }], fillspace: true },
					{ id: "yearofmanufacture", header: ["Năm Phát Hành", { content: "selectFilter" }], width: 140, css: { "text-align": "center" }, tooltip: "", editor: "text" },
					{ id: "time", header: ["Thời Lượng", { content: "selectFilter" }], width: 110, css: { "text-align": "center" }, tooltip: "", editor: "text" },
					{ id: "limitage", header: ["Độ Tuổi", { content: "selectFilter" }], width: 100, css: { "text-align": "center" }, tooltip: "", editor: "text" },
					{ id: "countryName", header: ["Quốc Gia", { content: "selectFilter" }], width: 140, css: { "text-align": "center" }, tooltip: "", editor: "text" },
					{ id: "poster", header: "Ảnh", width: 140, minHeight: 100, css: { "text-align": "center" }, tooltip: "", editor: "text", template: "<img src='https://zuhot-cinema-images.s3.amazonaws.com/poster-movie/#poster#' style='width:50px'/>" },

				],
				height: 450,
				scrollX: false,
				footer: true,
				fixedRowHeight: false, rowLineHeight: 60, rowHeight: 60,
				on: {
					onItemClick: function (id) {
						const selectedItem = this.getItem(id);
						if (selectedItem) {
							const movieId = selectedItem.id;
							MovieService.fillForm(movieId);
						}
					}
				}
			}]
		};

		return {
			view: "scrollview",
			scroll: "y",
			body: {
				rows: [form, table]
			}
		}
	};
	init() {
		MovieService.fillMovie();
	}


}