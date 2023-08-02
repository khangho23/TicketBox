SELECT 
	mo.id,
	name,
	yearofmanufacture,
	poster,
	time,
	describe,
	trailer,
	status,
	limitage
FROM movie AS mo
WHERE mo.status = '2';