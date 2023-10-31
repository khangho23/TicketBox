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
join langugeofmovie on langugeofmovie.movieid = Movie.id
join showtime on langugeofmovie.id = showtime.langugeofmovieid
join ticket on ticket.showtimeid = showtime.id
WHERE Movie.id = /* id */'MP00';