SELECT 
	showtime.id,
	showtime.movieId,
	showtime.starttime,
	showtime.price,
	showtime.showdate,
	showtime.dimensionid,
	showtime.roomid,
	room.name as room,
	dimension.name as dimension,
	branch.name as branch,
	branch.address as branchAddress,
	movie.name as movieName
FROM showtime 
JOIN movie on movie.id = showtime.movieid
JOIN dimension on dimension.id = showtime.dimensionid
JOIN room on room.id = showtime.roomid
JOIN branch on branch.id = room.branchid
ORDER BY showtime.id ASC