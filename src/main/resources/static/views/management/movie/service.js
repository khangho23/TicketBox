const responseType = await axios.get("/api/movieType");
const responseLanguage = await axios.get("/api/language");

class MovieService {


	async fillMovie() {
		const response = await axios.get("/api/movie/findAllMovieAdmin");
		const responseCountry = await axios.get("/api/country");

		$$("datatable").clearAll();
		$$("datatable").parse(response.data);

		const country = responseCountry.data.map(s => { return { id: s.id, value: s.name } });
		$$('country').define("options", country);
		$$('country').render();

		const languageList = responseLanguage.data.map(s => { return { id: s.id, value: s.name, selected: false } });
		$$('language').define("data", languageList);
		$$('language').render();

		const typeList = responseType.data.map((s) => { return { id: s.id, value: s.name, selected: false } });
		$$('type').define("data", typeList);
		$$('type').render();

		$$('poster').define("data", { poster: "" })

		$$("trailer").setValues({ src: "" });
	}

	async fillForm(movieId) {
		$$("Form").clear();
		$$("language").clearAll();
		$$("type").clearAll();

		const languageList = responseLanguage.data.map(s => { return { id: s.id, value: s.name, selected: false } });
		$$('language').define("data", languageList);
		$$('language').render();

		const typeList = responseType.data.map((s) => { return { id: s.id, value: s.name, selected: false } });
		$$('type').define("data", typeList);
		$$('type').render();

		const response = await axios.get("/api/movie/findMovieById", { params: { movieId: movieId } });
		$$("Form").parse(response.data);
		$$("country").setValue(response.data.countryid);
		$$("status").setValue(response.data.status);

		const language = response.data.language.map(s => { return { id: s.id, name: s.name, selected: false } });
		let chuoiLanguage = language.map(s => s.name).reduce((a, b) => a + ", " + b);
		$$('selectedLanguage').setValue(chuoiLanguage);

		const type = response.data.type.map((s) => { return { id: s.id, name: s.name, selected: false } });
		let chuoiType = type.map(s => s.name).reduce((a, b) => a + ", " + b);
		$$('selectedType').setValue(chuoiType);

		$$('director').setValue(response.data.director.map(s => { return "" + s.name }));
		$$('actor').setValue(response.data.actor.map(s => { return "" + s.name }));

		$$('poster').define("data", { poster: "<img src='https://zuhot-cinema-images.s3.amazonaws.com/poster-movie/" + response.data.poster + "' style='height:340px;width:290px' />" });

		const setLanguage = $$("language");
		if (language != null) {
			for (let i = 0; i < language.length; i++) {
				const item = setLanguage.getItem(language[i].id);
				item.selected = !item.selected;
				setLanguage.updateItem(language[i].id, item);
				const itemNode = setLanguage.getItemNode(language[i].id);
				if (item.selected) {
					itemNode.classList.add("selected-item");
					itemNode.style.backgroundColor = "#C6E2FF";
				} else {
					itemNode.classList.remove("selected-item");
				}
			}
		}

		const setType = $$("type");
		if (type != null) {
			for (let i = 0; i < type.length; i++) {
				const item = setType.getItem(type[i].id);
				item.selected = !item.selected;
				setType.updateItem(type[i].id, item);
				const itemNode = setType.getItemNode(type[i].id);
				if (item.selected) {
					itemNode.classList.add("selected-item");
					itemNode.style.backgroundColor = "#C6E2FF";
				} else {
					itemNode.classList.remove("selected-item");
				}
			}
		}

		$$("trailer").setValues({ src: "<iframe width='560' height='340' src='" + response.data.trailer + "' title='YouTube video player' frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share' allowfullscreen></iframe>" });
	}

	insert = async () => {
		let data = $$("Form").getValues();
		let dt = {
			id: data.id,
			name: data.name,
			yearofmanufacture: Number(data.yearofmanufacture),
			countryid: Number($$("country").getValue()),
			time: Number(data.time),
			describe: data.describe,
			trailer: data.trailer,
			status: data.status,
			poster: data.poster,
			arrayLanguage: $$("language").find(function (item) {
				return item.selected;
			}).map((s) => s.id),
			arrayType: $$("type").find(function (item) {
				return item.selected;
			}).map((s) => s.id),
			limitage: Number(data.limitage),
			actor: data.actor.split(",").map(s => { return { name: s } }),
			director: data.director.split(",").map(s => { return { name: s } }),
			arrayActor: [],
			arrayDirector: []
		};

		const upload = $$("poster").getValues();
		const formData = new FormData();
		const result = await fetch(upload.url);
		const blob = await result.blob();
		const option = { type: blob.type, lastModified: new Date() }
		var file = new File([blob], upload.file.name, option);
		console.log(file);
		const jsonBlob = new Blob([JSON.stringify(dt)], { type: 'application/json' });

		formData.append('json', jsonBlob, 'data.json');
		formData.append('file', file);

		try {
			await axios.post("/api/movie/insert", formData, {
				headers: {
					"Content-Type": "multipart/form-data",
					"Access-Control-Allow-Origin": "*",
				},
			}).then((e) => {
				console.log(e);
				webix.message("Thêm thành công.", "success");
			})
		} catch (e) {
			console.log(e.response.data);
			webix.message("" + e.response.data.message, "error");
		}

		$$("Form").clear();
		this.fillMovie();
	}

	update = async () => {
		let data = $$("Form").getValues();
		let dt = {
			id: data.id,
			name: data.name,
			yearofmanufacture: Number(data.yearofmanufacture),
			countryid: Number($$("country").getValue()),
			time: Number(data.time),
			describe: data.describe,
			trailer: data.trailer,
			status: data.status,
			poster: data.poster,
			arrayLanguage: $$("language").find(function (item) {
				return item.selected;
			}).map((s) => s.id),
			arrayType: $$("type").find(function (item) {
				return item.selected;
			}).map((s) => s.id),
			limitage: Number(data.limitage),
			actor: data.actor.split(",").map(s => { return { name: s } }),
			director: data.director.split(",").map(s => { return { name: s } }),
			arrayActor: [],
			arrayDirector: []
		};

		const upload = $$("poster").getValues();
		const formData = new FormData();
		const result = await fetch(upload.url);
		const blob = await result.blob();
		const option = { type: blob.type, lastModified: new Date() }
		var file = new File([blob], upload.file.name, option);
		console.log(file);
		const jsonBlob = new Blob([JSON.stringify(dt)], { type: 'application/json' });

		formData.append('json', jsonBlob, 'data.json');
		formData.append('file', file);

		try {
			await axios.put("/api/movie/update", formData, {
				headers: {
					"Content-Type": "multipart/form-data",
					"Access-Control-Allow-Origin": "*",
				},
			}).then((e) => {
				console.log(e);
				webix.message("Cập nhật thành công.", "success");
			})
		} catch (e) {
			console.log(e.response.data);
			webix.message("" + e.response.data.message, "error");
		}

		$$("Form").clear();
		this.fillMovie();
	}
}
export default new MovieService();