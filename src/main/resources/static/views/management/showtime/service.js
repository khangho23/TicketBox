class ShowtimeService {
    async fillShowtime() {
        const response = await axios.get("/api/showtime")
        $$("datatable").clearAll();
        $$("datatable").parse(response);
    }
    async fillForm(showtimeId) {
        const response = await axios.get("/api/showtime/" + showtimeId);
        $$("Form").parse(response.data);
    }
    async createShowtime() {
        if ($$("Form").validate()) {
            let data = $$("Form").getValues();
            let dt = {
                roomId: data.roomId,
                movieId: data.movieId,
                dimensionId: data.dimensionId,
                showDate: data.showDate,
                startTime: data.startTime,
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
    async deleteShowtime() {
        try {
            let data = $$("Form").getValues();
            const response = await axios.get("/api/showtime/deleteShowTime/" + data.id);
            (response.data === "SUCCESS") ? webix.alert("Xoá thành công").then(() => this.clearForm()) : console.log(response.data);
        } catch (error) {
            webix.alert(error.response.data, "alert-error");
        }
    }
    async updateShowtime() {
        try {
            let data = $$("Form").getValues();
            const response = await axios.post("/api/showtime/updateShowTime", data);
            (response.data === "SUCCESS") ? webix.alert("Cập nhật thành công").then(() => this.clearForm()) : console.log(response.data);
        } catch (error) {
            webix.alert(error.response.data, "alert-error");
        }
    }
    async clearForm() {
        $$("Form").parse({});
        $$("Form").clearValidation();
        this.fillShowtime();
    }
}
export default new ShowtimeService();