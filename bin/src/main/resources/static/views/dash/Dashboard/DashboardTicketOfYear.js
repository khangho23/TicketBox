import "//cdn.webix.com/libs/jet/webix-jet.js"
import DashboardService from "../Sevice/service.js";;
const JetView = webix.jet.JetView;

export default class ReviewsView extends JetView {

	data2 = {
		labels: [],
		datasets: [{
			label: 'Số lượng',
			data: [],
			backgroundColor: [
				'rgba(255, 99, 132, 1)',
				'rgba(54, 162, 235, 1)',
				'rgba(255, 206, 86, 1)',
				'rgba(75, 192, 192, 1)',
				'rgba(153, 102, 255, 1)',
				'rgba(255, 159, 64, 1)',
				'rgba(54, 47, 138, 1)',
				'rgba(118, 47, 138, 1)',
				'rgba(59, 181, 138, 1)',
				'rgba(255, 131, 160, 1)',
				'rgba(255, 255, 17, 1)',
				'rgba(199, 0, 17, 1)'
			],
			hoverOffset: 4
		}]
	};


	config() {
		return {
			width: 300,
			height: 380,
			rows: [{
				cols: [
					{
						view: "select",
						id: "selectinput",
						label: "Chọn năm:",
						options: [
							{ id: "2023", value: "2023" },
							{ id: "2022", value: "2022" },
						]
						, on: {
							onChange: () => {
								const inputValue = this.$$("selectinput").getValue();
								DashboardService.filldata(inputValue)
									.then((data) => {
										this.data2.datasets[0].data = data.map((item) => item.totalTicket);
										this.data2.labels = data.map((item) => 'Tháng ' + item.id);
										this.updateChart();
									})
									.catch(error => {
										console.error("Lỗi khi gọi API:", error);
									});
							}
						},
					}]
			},
			{
				template: "<div ><canvas id='myChartPie'></canvas></div>",
			},
			],
		}
	}
	init() {
		const currentYear = new Date().getFullYear().toString();
		this.$$("selectinput").setValue(currentYear);
		DashboardService.filldata(currentYear)
			.then((data) => {
				this.data2.datasets[0].data = data.map((item) => item.totalTicket);
				this.data2.labels = data.map((item) => 'Tháng ' + item.id);
				this.updateChart();
			})
			.catch((error) => {
				console.error("Lỗi khi gọi API:", error);
			});

		const ctx = document.getElementById('myChartPie');

		this.chart = new Chart(ctx, {
			type: 'doughnut',
			data: this.data2,
		});
	}
	updateChart() {
		this.chart.update();
	}
}