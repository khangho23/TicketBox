SELECT
	movie.id,
	countryid,
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
join country on Movie.countryid = country.id
WHERE Movie.id = /* id */'MP00';
