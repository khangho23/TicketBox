SELECT
	showtime.id,
	showtime.movieId,
	showtime.starttime,
	showtime.showdate,
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
WHERE showtime.showdate = /* showdate */'2023-08-19' and showtime.movieid = /* movieId */'MP01';