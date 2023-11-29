SELECT
	id,
	countryid,
	name,
	yearofmanufacture,
	poster,
	time,
	describe,
	trailer,
	status,
	limitage
FROM movie
WHERE status  = /* status */'LP01'
/*%if pageSize != null && page != null*/ LIMIT CAST(/* pageSize */4 AS INT) OFFSET (CAST(/* page */1 AS INT) - 1) * CAST(/* pageSize */4 AS INT) /*%end*/