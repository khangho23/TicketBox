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
/*%if status == "0" */
and movie.id not in (select movieconfig.movieid from movieconfig where movieconfig.movieid = movie.id and movieconfig.status = '1') 
/*%end*/
AND movieconfig.status = /* status */'1'
group by movie.id,countryid,name,yearofmanufacture,poster,time,describe,trailer,movieconfig.status,limitage
ORDER BY id ASC;