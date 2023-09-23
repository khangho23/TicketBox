// import {JetView} from "webix-jet";
import "//cdn.webix.com/libs/jet/webix-jet.js"
import DashboardPriceOfYear from "./Dashboard/DashboardPriceOfYear.js";
import DashboardTicketOfYear from "./Dashboard/DashboardTicketOfYear.js";
import DashboardMovie from "./Dashboard/DashboardMovie.js";
const JetView = webix.jet.JetView;

export default class TopView extends JetView {
	
	config() {
		return {
			type:"space", rows:[
				{
					type:"wide",
					cols:[ { $subview:DashboardPriceOfYear }, { $subview:DashboardTicketOfYear }]
				},{
					type:"wide",
					cols:[ { $subview:DashboardMovie}]
				}
//				{ type:"wide", cols:[
//					{ $subview:Projects },
//					{ type:"wide", rows:[
//						{ $subview:"dash.stats" },
//						{ $subview:"dash.projects" }
//					]}
//				]}
			]
		};
	}
}