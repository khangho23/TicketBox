class DashboardService {
	async filldata(year, branchName) {
		const response = await axios.get("/dashboard/findTotalPriceTicket", { params: { year: year, branchName: branchName } });
		return response.data; // Đảm bảo API trả về dữ liệu cần thiết
	}

	filldata4 = async () => {
		let { data: result } = await axios.get("/dashboard/statisticsTicketPriceByMovie",);
		$$("datatable").clearAll();
		$$("datatable").parse(result);
		return result;
	}

	async filldata5(movieName, year) {
		const response = await axios.get("/dashboard/statisticsTicketPriceByMovie2", { params: { movieName: movieName, year: year } });
		return response.data; // Đảm bảo API trả về dữ liệu cần thiết
	}

	async fillOption() {
		const response = await axios.get("/api/branch");
		$$("selectBranch").define("options", response.data.map(s => { return { id: s.name } }));
		$$("selectBranch").render();
		$$("selectBranch2").define("options", response.data.map(s => { return { id: s.name } }));
		$$("selectBranch2").render();
		return response.data.map(s => { return { id: s.name } });

	}
	async fillOption2() {
		const response = await axios.get("/dashboard/fillYear");
		$$("selectYear").define("options", response.data.map(s => { return { id: s.year } }));
		$$("selectYear").render();
		$$("selectYear2").define("options", response.data.map(s => { return { id: s.year } }));
		$$("selectYear2").render();
		return response.data.map(s => { return { id: s.name } });

	}

}
export default new DashboardService();