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