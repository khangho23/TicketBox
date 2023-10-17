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
FROM Movie
WHERE LOWER(name) LIKE /* name */'HA';