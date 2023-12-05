SELECT
	movie.id,
	countryid,
	name,
	yearofmanufacture,
	poster,
	time,
	describe,
	trailer,
	movieconfig.status,
	limitage
FROM Movie
JOIN movieconfig ON movieconfig.movieid = movie.id
WHERE LOWER(name) LIKE /* name */'cù lao xác sống' 
AND movieconfig.status = /* status */'1'
group by movie.id,countryid,name,yearofmanufacture,poster,time,describe,trailer,movieconfig.status,limitage
ORDER BY id ASC;