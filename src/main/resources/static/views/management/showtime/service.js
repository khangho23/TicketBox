class ShowtimeService {
    async init() {
        const response = await axios.get("/api/showtime")
        $$("datatable").clearAll();
        $$("datatable").parse(response);
    }
    async onClick(showtimeId) {
        const response = await axios.get("/api/showtime/" + showtimeId);
        $$("Form").parse(response.data);
    }
    async createShowtime() {
        if ($$("Form").validate()) {
            let data = $$("Form").getValues();
            let dt = {
                roomId: data.roomId,
                languageOfMovieId: data.languageOfMovieId,
                dimensionId: data.dimensionId,
                showDate: data.showDate,
                startTime: new Date(data.startTime).toLocaleTimeString(),
                price: data.price
            }
            try {
                const response = await axios.post("/api/showtime/createShowTime", dt);
                (response.data === "SUCCESS") ? webix.alert("Tạo thành công").then(() => this.clearForm()) : webix.alert(response.data, "alert-warning");
            } catch (error) {
                webix.alert(error.response.data, "alert-error");
            }
        }
    }
    deleteShowtime() {
        webix.confirm({
            title: "Bạn có chắc chắn muốn xoá ?",
            ok: "Có", cancel: "Không",
        }).then(async () => {
            try {
                let data = $$("Form").getValues();
                const response = await axios.get("/api/showtime/deleteShowTime/" + data.id);
                (response.data === "SUCCESS") ? webix.alert("Xoá thành công").then(() => this.clearForm()) : console.log(response.data);
            } catch (error) {
                webix.alert(error.response.data, "alert-error");
            }
        })
    }
    updateShowtime() {
        webix.confirm({
            title: "Bạn có muốn lưu thay đổi không ?",
            ok: "Có", cancel: "Không",
        }).then(async () => {
            try {
                let data = $$("Form").getValues();
                let dt = {
                    id: data.id,
                    roomId: data.roomId,
                    languageOfMovieId: data.languageOfMovieId,
                    dimensionId: data.dimensionId,
                    showDate: data.showDate,
                    startTime: new Date(data.startTime).toLocaleTimeString(),
                    price: data.price
                }
                const response = await axios.post("/api/showtime/updateShowTime", dt);
                (response.data === "SUCCESS") ? webix.alert("Cập nhật thành công").then(() => this.clearForm()) : console.log(response.data);
            } catch (error) {
                webix.alert(error.response.data, "alert-error");
            }
        })
    }
    async clearForm() {
        this.init();
        $$("Form").parse({});
        $$("Form").clearValidation();
    }
}
export default new ShowtimeService();