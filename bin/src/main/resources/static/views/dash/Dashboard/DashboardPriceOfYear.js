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
			width: 1000,
			height: 380,
			rows: [
				{
					cols: [
						{
							view: "select",
							id: "selectInput",
							label: "Chọn năm:",
							options: [
								{ id: "2023", value: "2023" },
								{ id: "2022", value: "2022" },
							],
							on: {
								onChange: () => {
									const inputValue = this.$$("selectInput").getValue();
									this.defaultInputValue=inputValue;
									DashboardService.filldata(inputValue)
										.then((data) => {
											this.data.datasets[0].label = "Doanh thu từng tháng năm "+inputValue;
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
					template: "<div><canvas id='myChart' style='height: 400px;'></canvas></div>",
				},
			],
		};
	}

	init() {
		const currentYear = new Date().getFullYear().toString();
		this.$$("selectInput").setValue(currentYear);
		DashboardService.filldata(currentYear)
			.then((data) => {
				this.data.datasets[0].label = "Doanh thu từng tháng năm "+currentYear;
				this.data.datasets[0].data = data.map((item) => item.totalPrice);
				this.data.labels = data.map((item) =>"T"+ item.id);
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
				scales: {
					y: {
						beginAtZero: true,
					},
				},
			},
		});
	}

	updateChart() {
		this.chart.update();
	}
}
