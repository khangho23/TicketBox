class DashboardService {
	async filldata(year) {
		const response = await axios.get("/dashboard/findTotalPriceTicket", { params: { year: year } });
		return response.data; // Đảm bảo API trả về dữ liệu cần thiết
	}
	
	filldata4 = async () => {
		let { data: result } = await axios.get("/dashboard/statisticsTicketPriceByMovie",);
		$$("datatable").clearAll();
		$$("datatable").parse(result);
		return result;
	}

	async filldata5(movieName,year) {
		const response = await axios.get("/dashboard/statisticsTicketPriceByMovie2", { params: { movieName:movieName,year: year } });
		return response.data; // Đảm bảo API trả về dữ liệu cần thiết
	}

}
export default new DashboardService();