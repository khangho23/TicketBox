
-- Hiển thị thông tin lịch chiếu của phim trong 1 ngày với mã movieId(mã phim) và showdate(ngày)

SELECT
	showtime.id,
	showtime.languageofmovieid,
	languageofmovie.movieid as movieid,
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
JOIN languageofmovie on languageofmovie.id = showtime.languageofmovieid
JOIN movie on movie.id = languageofmovie.movieid
JOIN dimension on dimension.id = showtime.dimensionid
JOIN room on room.id = showtime.roomid
JOIN branch on branch.id = room.branchid
WHERE TO_CHAR(showtime.showdate,'MM/DD/YYYY') = /* showdate */'2023-08-19' and languageofmovie.movieid = /* movieId */'MP01';