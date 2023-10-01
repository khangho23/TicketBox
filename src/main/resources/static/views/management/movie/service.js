class MovieService {
	async fillMovie() {
		const response = await axios.get("/api/movie/findAllMovieAdmin");
		const responseLanguage = await axios.get("/api/language");
		const responseCountry = await axios.get("/api/country");
		const responseType = await axios.get("/api/movieType")
		$$("datatable").clearAll();
		$$("datatable").parse(response);
		const language = responseLanguage.data.map(s => { return { id: s.id, value: s.name } });
		$$('language').define("data", language);
		$$('language').render();
		const country = responseCountry.data.map(s => { return { id: s.id, value: s.name } });
		$$('country').define("options", country);
		$$('country').render();
		const type = responseType.data.map((s, key) => { return { id: key, idType: s.id, value: s.name } });
		console.log(type);
		$$('type').define("data", type);
		$$('type').render();

		$$('poster').define("data", { poster: "<img src='https://image.spreadshirtmedia.net/image-server/v1/products/T1459A839PA4459PT28D302767869W8333H10000/views/1,width=378,height=378,appearanceId=839,backgroundColor=F2F2F2/cinema-cinema-cinema-amanti-del-cinema.jpg' style='height:200px' />" })
	}
	async fillForm(movieId) {
		const response = await axios.get("/api/movie/findMovieById", { params: { movieId: movieId } });
		$$("Form").parse(response.data);
		console.log(response.data);
		$$("country").setValue(response.data.countryid);
		$$("status").setValue(response.data.status);
		const language = response.data.language.map(s => { return { id: s.id, name: s.name } });

		let chuoiLanguage = '';

		for (let i = 0; i < language.length; i++) {
			chuoiLanguage += language[i].name;
			if (i < language.length - 1) {
				chuoiLanguage += ', ';
			}
		}
		$$('selectedLanguage').setValue(chuoiLanguage);

		const type = response.data.type.map(s => { return { id: s.id, name: s.name } });

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
			actor: data.actor,
			yearofmanufacture: data.yearofmanufacture,
			director: data.director,
			country: data.countryid,
			time: data.time,
			describe: data.describe,
			trailer: data.trailer,
			status: data.status,
			poster: data.poster,

		};
		console.log(dt);
	}
}
export default new MovieService();