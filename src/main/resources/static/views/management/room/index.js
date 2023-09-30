// import {JetView} from "webix-jet";
import "//cdn.webix.com/libs/jet/webix-jet.js"

const JetView = webix.jet.JetView;

export default class TopView extends JetView {
	
	config() {
		return {
			type:"space", rows:[
				
			]
		};
	}
}