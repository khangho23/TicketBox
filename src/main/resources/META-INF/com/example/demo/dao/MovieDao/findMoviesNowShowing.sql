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
JOIN showtime AS sh ON sh.movieid = mo.id AND sh.showdate::date = CURRENT_DATE
-- 1 là trạng thái đang chiếu
WHERE mo.status = '1'