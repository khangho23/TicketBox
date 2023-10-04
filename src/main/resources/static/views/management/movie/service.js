
class MovieService {
	async fillMovie() {
		const response = await axios.get("/api/movie/findAllMovieAdmin");
		const responseLanguage = await axios.get("/api/language");
		const responseCountry = await axios.get("/api/country");
		const responseType = await axios.get("/api/movieType")
		$$("datatable").clearAll();
		$$("datatable").parse(response.data);
		const language = responseLanguage.data.map(s => { return { id: s.id, value: s.name } });
		$$('language').define("data", language);
		$$('language').render();
		const country = responseCountry.data.map(s => { return { id: s.id, value: s.name } });
		$$('country').define("options", country);
		$$('country').render();
		const type = responseType.data.map((s) => { return { id: s.id, value: s.name } });
		$$('type').define("data", type);
		$$('type').render();

		$$('poster').define("data", { poster: "<div></div>" })
	}
	async fillForm(movieId) {
		const response = await axios.get("/api/movie/findMovieById", { params: { movieId: movieId } });
		$$("Form").parse(response.data);
		console.log(response.data);
		$$("country").setValue(response.data.countryid);
		$$("status").setValue(response.data.status);
		const language = response.data.language.map(s => { return { id: s.id, name: s.name } });

		let chuoiLanguage = language.map(s => s.name).reduce((a, b) => a + ", " + b);

		$$('selectedLanguage').setValue(chuoiLanguage);

		const type = response.data.type.map((s) => { return { id: s.id, name: s.name } });

		let chuoiType = '';
		for (let i = 0; i < type.length; i++) {
			chuoiType += type[i].name;
			if (i < type.length - 1) {
				chuoiType += ', ';
			}
		}
		$$('selectedType').setValue(chuoiType);


		$$('director').setValue(response.data.director.map(s => { return "" + s.name }));
		$$('actor').setValue(response.data.actor.map(s => { return "" + s.name }));

		$$('poster').define("data", { poster: "<img src='/images/movie/" + response.data.poster + "' style='height:200px' />" })

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
				webix.alert("Thêm thành công");
			})
		} catch (e) {
			console.log(e.response.data);
			webix.alert("Thêm thất bại");
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
				webix.alert("Cập nhật thành công");
			})
		} catch (e) {
			console.log(e.response.data);
			webix.alert("Cập nhật thất bại");
		}
		$$("Form").clear();
		this.fillMovie();
	}
}
export default new MovieService();