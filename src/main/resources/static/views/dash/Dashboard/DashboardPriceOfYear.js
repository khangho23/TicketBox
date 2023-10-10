import "//cdn.webix.com/libs/jet/webix-jet.js";
import DashboardService from "../Sevice/service.js";
const JetView = webix.jet.JetView;

export default class ProjectsView extends JetView {
	data = {
		labels: [],
		datasets: [
			{
				label: "",
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
				borderColor: [],
				borderWidth: 1,
			},
		],
	};

	config() {
		return {
			width: 900,
			height: 400,
			rows: [
				{
					cols: [
						{
							view: "select",
							id: "selectYear",
							label: "Chọn năm:",
							options: [],
							on: {
								onChange: () => {
									const year = this.$$("selectYear").getValue();
									const branch = this.$$("selectBranch").getValue();
									DashboardService.filldata(year, branch)
										.then((data) => {
											this.data.datasets[0].label = "Doanh thu từng tháng năm " + year;
											this.data.datasets[0].data = data.map((item) => item.totalPrice);
											this.data.labels = data.map((item) => "T " + item.id);
											this.updateChart();
										})
										.catch((error) => {
											console.error("Lỗi khi gọi API:", error);
										});
								},
							},
						}, {
							view: "select",
							id: "selectBranch",
							label: "Chọn chi nhánh:",
							options: [],
							labelWidth: 120,
							on: {
								onChange: () => {
									const branch = this.$$("selectBranch").getValue();
									const year = this.$$("selectYear").getValue();
									DashboardService.filldata(year, branch)
										.then((data) => {
											this.data.datasets[0].label = "Doanh thu từng tháng năm " + year;
											this.data.datasets[0].data = data.map((item) => item.totalPrice);
											this.data.labels = data.map((item) => "T " + item.id);
											this.updateChart();
										})
										.catch((error) => {
											console.error("Lỗi khi gọi API:", error);
										});
								},
							},
						},
					],
				},
				{
					template: "<div><canvas id='myChart' style='height: 450px;'></canvas></div>",
					css: 'dashboard3'
				},
			],
		};
	}

	async init() {
		await DashboardService.fillOption2();
		const optionBranch = await DashboardService.fillOption();
		const currentYear = new Date().getFullYear().toString();
		this.$$("selectYear").setValue(currentYear);
		this.$$("selectBranch").setValue(optionBranch[1].id);
		DashboardService.filldata(currentYear, optionBranch[1].id)
			.then((data) => {
				this.data.datasets[0].label = "Doanh thu từng tháng năm " + currentYear;
				this.data.datasets[0].data = data.map((item) => item.totalPrice);
				this.data.labels = data.map((item) => "T" + item.id);
				this.updateChart();
			})
			.catch((error) => {
				console.error("Lỗi khi gọi API:", error);
			});

		const ctx = document.getElementById('myChart');
		ctx.style.width = '1200px';
		this.chart = new Chart(ctx, {
			width: 10,
			type: "bar",
			data: this.data,
			options: {
				plugins: {
					legend: {
						labels: {
							color: "white",
						},
					},
				},
				scales: {
					y: {
						beginAtZero: true,
						ticks: {
							color: "white"
						},
						grid: {
							color: "white",
						}
					},
					x: {
						beginAtZero: true,
						ticks: {
							color: "white"
						},
						grid: {
							color: "white",
						}
					},
				},
			},
		});
	}

	updateChart() {
		this.chart.update();
	}
}
