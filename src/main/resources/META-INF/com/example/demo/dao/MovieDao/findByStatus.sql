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
FROM movie
join movieconfig on movieconfig.movieid = movie.id
WHERE 
movieconfig.status  = /* status */'0'
/*%if status == "0" */
and movie.id not in (select movieconfig.movieid from movieconfig where movieconfig.movieid = movie.id and movieconfig.status = '1') 
/*%end*/
group by movie.id,countryid,name,yearofmanufacture,poster,time,describe,trailer,movieconfig.status,limitage
/*%if pageSize != null && page != null*/ LIMIT CAST(/* pageSize */4 AS INT) OFFSET (CAST(/* page */1 AS INT) - 1) * CAST(/* pageSize */4 AS INT) /*%end*/
