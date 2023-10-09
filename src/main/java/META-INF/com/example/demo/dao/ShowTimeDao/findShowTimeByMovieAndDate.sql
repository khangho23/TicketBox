
-- Hiển thị thông tin lịch chiếu của phim trong 1 ngày với mã movieId(mã phim) và showdate(ngày)

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
	branch.id as branchId,
	branch.name as branch,
	branch.address as branchAddress
FROM showtime 
JOIN movie on movie.id = showtime.movieid
JOIN dimension on dimension.id = showtime.dimensionid
JOIN room on room.id = showtime.roomid
JOIN branch on branch.id = room.branchid
WHERE TO_CHAR(showtime.showdate,'MM/DD/YYYY') = /* showdate */'2023-08-19' and showtime.movieid = /* movieId */'MP01';