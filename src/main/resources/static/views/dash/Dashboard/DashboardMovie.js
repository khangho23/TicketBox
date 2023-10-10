
import "//cdn.webix.com/libs/jet/webix-jet.js"
import DashboardService from "../Sevice/service.js";
const JetView = webix.jet.JetView;


export default class StatsView extends JetView {
	data = {
		labels: [],
		datasets: [
			{
				label: "Doanh thu từng tháng ",
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
	data2 = {
		labels: [],
		datasets: [
			{
				label: "Số vé từng tháng ",
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
		const self = this;
		return {
			rows: [
				{
					template: "<h1 style='font-family:Bookman'>Thống kê Phim</h1>",
					height: 60
				},
				{
					cols: [
						{
							view: "datatable",
							css: "tabledash",
							width: 700,
							height: 480,
							id: "datatable",
							columns: [
								{ id: "movieName", header: ["Tên Phim", { content: "textFilter" }], fillspace: true, footer: "Tổng" },
								{ id: "year", header: ["Năm", { content: "selectFilter" }], width: 90, css: { "text-align": "center" }, tooltip: "", editor: "text" },
								{
									id: "totalPrice", header: ["Doanh thu", { content: "numberFilter" }], width: 100, sort: "int", footer: { content: "summColumn" },
									format: (value) => { return webix.Number.format(value, { groupSize: 3, groupDelimiter: ".", decimalSize: 0, decimalDelimiter: "", sufix: "₫" }); }
								},
								{ id: "totalTicket", header: ["Tổng vé", { content: "numberFilter" }], width: 80, sort: "int", footer: { content: "summColumn" } }
							],
							scrollX: false,
							footer: true,
							on: {
								onItemClick: function (id) {
									const selectedItem = this.getItem(id);
									if (selectedItem) {
										const movieName = selectedItem.movieName;
										const year = selectedItem.year;
										DashboardService.filldata5(movieName, year)
											.then((data) => {
												self.data.datasets[0].data = data.map((item) => item.totalPrice);
												self.data.labels = data.map((item) => item.month);
												self.data.datasets[0].label = "Doanh thu năm " + year + " của " + movieName;
												self.data2.datasets[0].data = data.map((item) => item.totalTicket);
												self.data2.labels = data.map((item) => item.month);
												self.data2.datasets[0].label = "Tổng vé năm " + year + " của " + movieName;
												self.updateChart();
											})
											.catch((error) => {
												console.error("Lỗi khi gọi API:", error);
											});
									}
								}
							}
						}, {
							rows: [{
								template: "<div><canvas id='myChart2' style='height: 580px;color:white !important'></canvas></div>",
								css: 'dashboard1'
							}, {
								template: "<div><canvas id='myChart3' style='height: 580px;'></canvas></div>",
								css: 'dashboard2'
							}]
						}

					]
				}]
		}

	};
	init() {
		DashboardService.filldata4().then((data1) => {
			DashboardService.filldata5(data1[0].movieName, data1[0].year)
				.then((data) => {
					this.data.datasets[0].data = data.map((item) => item.totalPrice);
					this.data.labels = data.map((item) => item.month);
					this.data2.datasets[0].data = data.map((item) => item.totalTicket);
					this.data.datasets[0].label = "Doanh thu năm " + data1[0].year + " của " + data1[0].movieName;
					this.data2.labels = data.map((item) => item.month);
					this.data2.datasets[0].label = "Tổng vé năm " + data1[0].year + " của " + data1[0].movieName;
					this.updateChart();
				})
				.catch((error) => {
					console.error("Lỗi khi gọi API:", error);
				});
		});
		const chart = this.$$("chart");


		const ctx = document.getElementById('myChart2');
		ctx.style.width = '1500px';
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
		const ctx2 = document.getElementById('myChart3');
		ctx2.style.width = '1500px';
		this.chart2 = new Chart(ctx2, {
			width: 10,
			type: "bar",
			data: this.data2,
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
		this.chart2.update();
		this.chart.update();
	}
}