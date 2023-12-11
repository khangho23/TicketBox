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
WHERE movieconfig.status  = /* status */'0'
group by movie.id,countryid,name,yearofmanufacture,poster,time,describe,trailer,movieconfig.status,limitage
/*%if pageSize != null && page != null*/ LIMIT CAST(/* pageSize */4 AS INT) OFFSET (CAST(/* page */1 AS INT) - 1) * CAST(/* pageSize */4 AS INT) /*%end*/
