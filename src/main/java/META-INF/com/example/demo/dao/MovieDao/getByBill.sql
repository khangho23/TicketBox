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
join languageofmovie on languageofmovie.movieid = Movie.id
join showtime on languageofmovie.id = showtime.languageofmovieid
join ticket on ticket.showtimeid = showtime.id
join bill on bill.id = ticket.billid
WHERE bill.id = /* id */0 LIMIT 1;