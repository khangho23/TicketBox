class PaymentService {
	async init() {
		const response = await axios.get("/api/movie/findAllMovieAdmin");

		$$("datatable").clearAll();
		$$("datatable").parse(response.data);
    }
}
export default new PaymentService();