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
                            { id: "id", header: ["Mã Xuất Chiếu", { content: "textFilter" }], fillspace: true },
                            { id: "roomName", header: ["Tên Phòng", { content: "selectFilter" }], width: 140, css: { "text-align": "center" }, tooltip: "", editor: "text" },
                            { id: "movieName", header: ["Tên Phim", { content: "textFilter" }], fillspace: true },
                            { id: "branchName", header: ["Chi Nhánh", { content: "selectFilter" }], width: 140, css: { "text-align": "center" }, tooltip: "", editor: "text" },
                            { id: "showDate", header: ["Ngày Chiếu", { content: "selectFilter" }], width: 140, css: { "text-align": "center" }, tooltip: "", editor: "text" },
                            { id: "startTime", header: ["Thời Gian Bắt Đầu", { content: "selectFilter" }], width: 140, css: { "text-align": "center" }, tooltip: "", editor: "text" },
                            { id: "dimensionName", header: ["Độ Phân Giải", { content: "selectFilter" }], width: 140, css: { "text-align": "center" }, tooltip: "", editor: "text" },
                            { id: "price", header: ["Giá", { content: "textFilter" }], fillspace: true },
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
                                    ShowtimeService.fillForm(showtimeId);
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
                                height: 700,
                                padding: 10,
                                elements: [
                                    { view: "text", name: "id", id: "id", label: "Mã xuất chiếu", disabled: true },
                                    { view: "text", name: "roomId", id: "roomId", label: "Mã phòng", invalidMessage: "Mã phòng không hợp lệ vui lòng nhập lại" },
                                    { view: "text", name: "movieId", id: "movieId", label: "Mã phim", invalidMessage: "Mã phim không hợp lệ vui lòng nhập lại" },
                                    { view: "text", name: "dimensionId", label: "Độ phân giải", invalidMessage: "Độ phân giải không hợp lệ vui lòng nhập lại" },
                                    { view: "text", name: "showDate", label: "Ngày chiếu", invalidMessage: "Ngày chiếu không được để trống vui lòng nhập lại" },
                                    { view: "text", name: "startTime", label: "Giờ chiếu", invalidMessage: "Giờ chiếu không hợp lệ vui lòng nhập lại" },
                                    { view: "text", name: "price", label: "Giá", invalidMessage: "Giá không hợp lệ vui lòng nhập lại" },
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
                                    labelPosition: "top",
                                    labelWidth: 120,
                                    bottomPadding: 15
                                },
                                rules: {
                                    "roomId": webix.rules.isNotEmpty,
                                    "movieId": webix.rules.isNotEmpty,
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
        ShowtimeService.fillShowtime();
    }
}