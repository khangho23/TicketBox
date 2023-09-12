SELECT showtime.showdate, showtime.starttime, dimension.name AS dimension,
	   branch.name, branch.address
FROM showtime
LEFT JOIN dimension ON dimension.id = showtime.dimensionid
JOIN room ON room.id = showtime.roomid
JOIN branch ON branch.id = room.branchid

WHERE showtime.movieid = /* movieId */'MP01'