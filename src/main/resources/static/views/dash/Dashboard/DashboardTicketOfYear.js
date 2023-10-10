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
			width: 380,
			rows: [{
				cols: [
					{
						view: "select",
						id: "selectYear2",
						label: "Chọn năm:",
						labelWidth: 80,
						width: 140,
						options: []
						, on: {
							onChange: () => {
								const year = this.$$("selectYear2").getValue();
								const branch = this.$$("selectBranch2").getValue();
								DashboardService.filldata(year, branch)
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
					}, {
						view: "select",
						id: "selectBranch2",
						label: "Chọn chi nhánh:",
						labelWidth: 115,
						options: [],
						on: {
							onChange: () => {
								const branch = this.$$("selectBranch2").getValue();
								const year = this.$$("selectYear2").getValue();
								DashboardService.filldata(year, branch)
									.then((data) => {
										this.data2.datasets[0].data = data.map((item) => item.totalTicket);
										this.data2.labels = data.map((item) => 'T ' + item.id);
										this.updateChart();
									})
									.catch((error) => {
										console.error("Lỗi khi gọi API:", error);
									});
							},
						},
					},]
			},
			{
				template: "<div ><canvas id='myChartPie'></canvas></div>",
				css: 'dashboard4'
			},
			],
		}
	}
	async init() {
		await DashboardService.fillOption2();
		const optionBranch = await DashboardService.fillOption();
		const currentYear = new Date().getFullYear().toString();
		this.$$("selectYear2").setValue(currentYear);
		this.$$("selectBranch2").setValue(optionBranch[1].id);
		DashboardService.filldata(currentYear, optionBranch[1].id)
			.then((data) => {
				this.data2.datasets[0].data = data.map((item) => item.totalTicket);
				this.data2.labels = data.map((item) => 'T ' + item.id);
				this.updateChart();
			})
			.catch((error) => {
				console.error("Lỗi khi gọi API:", error);
			});

		const ctx = document.getElementById('myChartPie');

		this.chart = new Chart(ctx, {
			type: 'doughnut',
			data: this.data2,
			options: {
				plugins: {
					legend: {
						labels: {
							color: "white",
						},
					},
				},
			},
		});
	}
	updateChart() {
		this.chart.update();
	}
}