import "//cdn.webix.com/libs/jet/webix-jet.js"
import MovieService from "./service.js";
const JetView = webix.jet.JetView;

export default class Movie extends JetView {

	config() {

		const uploadImage = {
			rows: [{
				view: "template",
				template: "#poster#",
				id: "poster",
				name: "poster",
				autoheight: true
			}, {
				view: "uploader",
				id: "upload",
				name: "upload",
				inputWidth: 130,
				value: "View an image",
				accept: "image/jpeg, image/png, image/jpg",
				autosend: false,
				multiple: true,
				on: {
					onBeforeFileAdd: async function (upload) {
						var file = upload.file;
						var reader = new FileReader();
						reader.onload = async function (event) {
							$$("poster").define("data", {
								poster: "<img src='" + event.target.result + "' style='height:200px' />",
								url: event.target.result,
								file: file
							});
						};
						reader.readAsDataURL(file)
						return false;
					}
				}
			}]
		};
		const form = {
			view: "form",
			id: "qlp",
			rows: [
				{
					view: "form",
					id: "Form",
					height: 850,
					padding: 10,
					elements: [
						{
							cols: [{ view: "text", name: "id", id: "id", label: "Mã Phim", invalidMessage: "Id can not be empty", labelWidth: 120 },
							{ view: "text", name: "name", id: "name", label: "Tên Phim", invalidMessage: "Name can not be empty", labelWidth: 120 },
							{ view: "text", name: "yearofmanufacture", id: "yearofmanufacture", label: "Năm Khởi Chiếu", labelWidth: 120 },]
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
										readonly: true
									},
									{
										view: "list",
										id: "language",
										select: false,
										data: [],
										height: 200,
										scrollX: false,
										footer: true,
										on: {
											onItemClick: function (id) {
												const item = this.getItem(id);
												item.selected = !item.selected;
												this.updateItem(id, item);
												const itemNode = this.getItemNode(id);
												console.log(itemNode)
												if (item.selected) {
													itemNode.classList.add("selected-item");
													itemNode.style.backgroundColor = "#C6E2FF";
												} else {
													itemNode.classList.remove("selected-item");
												}
												const selectedItems = this.find(item => item.selected).map(item => item.value);
												$$("selectedLanguage").setValue(selectedItems.join(", "));
												const language = this.find(item => item.selected).map(item => { return { id: item.id, name: item.value } });
												console.log(language)

											}
										}
									}
								]
							}, {
								rows: [
									{
										view: "text",
										label: "Thể Loại",
										id: "selectedType",
										readonly: true
									},
									{
										view: "list",
										id: "type",
										select: false,
										data: [],
										height: 200,
										scrollX: false,
										footer: true,
										value: [],
										on: {
											onItemClick: function (id) {
												const item = this.getItem(id);
												item.selected = !item.selected;
												this.updateItem(id, item);
												const itemNode = this.getItemNode(id);
												console.log(itemNode)
												if (item.selected) {
													itemNode.classList.add("selected-item");
													itemNode.style.backgroundColor = "#C6E2FF";
												} else {
													itemNode.classList.remove("selected-item");
												}

												const selectedItems = this.find(item => item.selected).map(item => item.value);
												const type = this.find(item => item.selected).map(item => { return { id: item.id, name: item.value } });
												console.log(type)
												$$("selectedType").setValue(selectedItems.join(", "));
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
								{ view: "button", value: "Sửa", css: "webix_primary", click: MovieService.update },
								{
									view: "button", value: "Mới", css: "webix_primary", click: function () {
										$$("Form").clear();
									}
								}
							]
						}
					],
					rules: {
						"id": webix.rules.isNotEmpty,
						"name": webix.rules.isNotEmpty,
						"yearormanufacture": webix.rules.isNotEmpty,

					},
					elementsConfig: {
						labelPosition: "top",
						labelWidth: 140,
						bottomPadding: 2
					}

				}]
		};


		const table = {
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
		};

		return {
			view: "scrollview",
			scroll: "y",
			body: {
				rows: [uploadImage, form, table]
			}
		}
	};
	init() {
		MovieService.fillMovie();
	}

}