// import {JetView} from "webix-jet";
import "//cdn.webix.com/libs/jet/webix-jet.js"

const JetView = webix.jet.JetView;

var menu_data = [
	{id: "dashboard", icon: "mdi mdi-view-dashboard", value: "Dashboards",  data:[
		{ id: "dashboard1", value: "Dashboard 1"},
		{ id: "dashboard2", value: "Dashboard 2"}
	]},
	{id: "layouts", icon: "mdi mdi-view-column", value:"Layouts", data:[
		{ id: "accordions", value: "Accordions"},
		{ id: "portlets", value: "Portlets"}
	]},
	{id: "tables", icon: "mdi mdi-table", value:"Data Tables", data:[
		{ id: "tables1", value: "Datatable"},
		{ id: "tables2", value: "TreeTable"},
		{ id: "tables3", value: "Pivot"}
	]},
	{id: "uis", icon: "mdi mdi-puzzle", value:"UI Components", data:[
		{ id: "dataview", value: "DataView"},
		{ id: "list", value: "List"},
		{ id: "menu", value: "Menu"},
		{ id: "tree", value: "Tree"}
	]},
	{id: "tools", icon: "mdi mdi-calendar", value:"Tools", data:[
		{ id: "kanban", value: "Kanban Board"},
		{ id: "pivot", value: "Pivot Chart"},
		{ id: "scheduler", value: "Calendar"}
	]},
	{id: "forms", icon: "mdi mdi-pencil", value:"Forms",  data:[
		{ id: "buttons", value: "Buttons"},
		{ id: "selects", value: "Select boxes"},
		{ id: "inputs", value: "Inputs"}
	]},
	{id: "demo", icon: "mdi mdi-book", value:"Documentation"}
];

export default class Actor extends JetView {

    config() {
        return {
            rows: [
                { view: "toolbar", padding:3, elements: [
                  { view: "icon", icon: "mdi mdi-menu", click: function(){
                     $$("$sidebar1").toggle();
                   }
                  },
                  { view: "label", label: "My App"},
                  {},
                  { view: "icon", icon: "mdi mdi-comment",  badge:4},
                  { view: "icon", icon: "mdi mdi-bell",  badge:10}
                ]
                },
                { cols:[
                  {
                    view: "sidebar",
                    data: menu_data,
                    on:{
                      onAfterSelect: function(id){
                        webix.message("Selected: "+this.getItem(id).value)
                      }
                    }
                  },
                  { template: ""}
                ]}
              ]
        };
    }
}