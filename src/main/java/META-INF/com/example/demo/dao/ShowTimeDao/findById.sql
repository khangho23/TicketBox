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
	branch.address as branchAddress
FROM showtime 
JOIN movie on movie.id = showtime.movieid
JOIN dimension on dimension.id = showtime.dimensionid
JOIN room on room.id = showtime.roomid
JOIN branch on branch.id = room.branchid
WHERE showtime.id = /* id */'1'