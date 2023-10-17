import InventoryApp from './app.js';

webix.ready(function () {
    const app = new InventoryApp({});
    app.render();
});