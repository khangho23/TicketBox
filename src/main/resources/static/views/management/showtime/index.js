import "//cdn.webix.com/libs/jet/webix-jet.js"
import ShowtimeService from "./service.js";
const JetView = webix.jet.JetView;
export default class Showtime extends JetView {
    config() {
        return {
            view: "scrollview",
            scroll: "y",
            body: {
                rows: [
                    {
                        view: "datatable",
                        id: "datatable",
                        columns: [
                            { id: "id", header: ["Mã Xuất Chiếu", { content: "textFilter" }] },
                            { id: "roomName", header: ["Tên Phòng", { content: "selectFilter" }] },
                            { id: "movieName", header: ["Tên Phim", { content: "textFilter" }], fillspace: true },
                            { id: "branchName", header: ["Chi Nhánh", { content: "selectFilter" }], fillspace: true },
                            { id: "showDate", header: ["Ngày Chiếu", { content: "textFilter" }], format: webix.Date.dateToStr("%d/%m/%Y") },
                            { id: "startTime", header: ["Thời Gian Bắt Đầu", { content: "textFilter" }], fillspace: true, format: "%H:%i:%s" },
                            { id: "languageName", header: ["Ngôn ngữ", { content: "selectFilter" }], fillspace: true },
                            { id: "dimensionName", header: ["Độ Phân Giải", { content: "selectFilter" }], fillspace: true },
                            { id: "price", header: ["Giá", { content: "textFilter" }], numberFormat: "1,111đ" },
                        ],
                        height: 300,
                        scrollX: false,
                        footer: true,
                        fixedRowHeight: false, rowLineHeight: 60, rowHeight: 60,
                        on: {
                            onItemClick: function (id) {
                                const selectedItem = this.getItem(id);
                                if (selectedItem) {
                                    const showtimeId = selectedItem.id;
                                    ShowtimeService.onClick(showtimeId);
                                }
                            }
                        }
                    },
                    {
                        view: "form",
                        id: "qlp",
                        rows: [
                            {
                                view: "form",
                                id: "Form",
                                height: 600,
                                padding: 10,
                                elements: [
                                    { view: "text", name: "id", id: "id", label: "Mã xuất chiếu", disabled: true, labelPosition: "top", hidden:true },
                                    {
                                        view: "combo", label: "Số phòng", name: "roomId", invalidMessage: "Vui lòng chọn số phòng", required: true, labelPosition: "top",
                                        options: {
                                            view: "suggest",
                                            body: {
                                                view: "list",
                                                id: "room",
                                                data: [],
                                                template: `#name# - (#branchName#)`,
                                                yCount: 5
                                            }
                                        },
                                        on: {
                                            onAfterRender: async function () {
                                                const { data: result } = await axios.get("/api/room");
                                                $$("room").parse(result);
                                            }
                                        },
                                    },
                                    {
                                        view: "combo", label: "Phim - Ngôn ngữ", name: "languageOfMovieId", invalidMessage: "Vui lòng chọn ngôn ngữ phim", required: true, labelPosition: "top",
                                        options: {
                                            view: "suggest",
                                            body: {
                                                view: "list",
                                                id: "language",
                                                data: [],
                                                template: "#id# - #movieName# (#languageName#)",
                                                yCount: 5
                                            }
                                        },
                                        on: {
                                            onAfterRender: async function () {
                                                const { data: result } = await axios.get("/api/languageOfMovie");
                                                $$("language").parse(result);
                                            }
                                        },
                                    },
                                    {
                                        view: "combo", label: "Độ phân giải", name: "dimensionId", invalidMessage: "Vui lòng chọn độ phân giải", required: true, labelPosition: "top",
                                        options: {
                                            view: "suggest",
                                            body: {
                                                view: "list",
                                                id: "dimension",
                                                data: [],
                                                template: "#name#",
                                                yCount: 5
                                            }
                                        },
                                        on: {
                                            onAfterRender: async function () {
                                                const { data: result } = await axios.get("/api/dimension");
                                                $$("dimension").parse(result);
                                            }
                                        },
                                    },
                                    { view: "datepicker", name: "showDate", id: "showDate", label: "Ngày chiếu", timepicker: true, format: "%d/%m/%Y", required: true, invalidMessage: "Vui lòng chọn ngày chiếu", labelPosition: "top" },
                                    { view: "datepicker", type: "time", name: "startTime", id: "startTime", label: "Giờ chiếu", timepicker: true, format: "%H:%i:%s", required: true, invalidMessage: "Vui lòng chọn giờ chiếu", labelPosition: "top" },
                                    {
                                        view: "text", name: "price", label: "Giá", invalidMessage: "Vui lòng chỉ nhập số", format: "1,111", required: true, labelPosition: "top"
                                    },
                                    {
                                        cols: [
                                            { view: "button", value: "Thêm", css: "webix_primary", click: (() => ShowtimeService.createShowtime()) },
                                            { view: "button", value: "Xóa", click: (() => ShowtimeService.deleteShowtime()) },
                                            { view: "button", value: "Sửa", click: (() => ShowtimeService.updateShowtime()) },
                                            { view: "button", value: "Mới", click: (() => ShowtimeService.clearForm()) }
                                        ]
                                    }
                                ],
                                elementsConfig: {
                                    labelWidth: 120,
                                    bottomPadding: 15
                                },
                                rules: {
                                    "roomId": webix.rules.isNotEmpty,
                                    "languageOfMovieId": webix.rules.isNotEmpty,
                                    "dimensionId": webix.rules.isNotEmpty,
                                    "showDate": webix.rules.isNotEmpty,
                                    "startTime": webix.rules.isNotEmpty,
                                    "price": webix.rules.isNotEmpty,
                                    "price": webix.rules.isNumber,
                                }
                            }]
                    }
                ]
            }
        }

    }
    init() {
        ShowtimeService.init();
    }
}