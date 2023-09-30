SELECT movie.id,
	country.name as countryName,
	movie.name,
	yearofmanufacture,
	poster,
	time,
	describe,
	trailer,
	status,
	limitage
FROM Movie
join country on movie.countryid = country.id
