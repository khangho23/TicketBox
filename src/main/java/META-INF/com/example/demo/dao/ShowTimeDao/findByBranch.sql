SELECT 
	showtime.id,
	showtime.roomid,
	showtime.dimensionid,
	showtime.showdate,
	showtime.starttime,
	showtime.price,
	showtime.languageofmovieid,
	room.name as room,
	dimension.name as dimensionName,
	movie.id as movieid,
	movie.name as movieName,
	language.name as languageName,
	branch.id as branchId,
	branch.name as branchname,
	branch.address as branchAddress
FROM showtime
JOIN languageofmovie on languageofmovie.id = showtime.languageofmovieid
JOIN language on language.id = languageofmovie.languageid
JOIN movie on movie.id = languageofmovie.movieid
JOIN dimension on dimension.id = showtime.dimensionid
JOIN room on room.id = showtime.roomid
JOIN branch on room.branchid = branch.id
WHERE branch.id = /* branchId */'cn1'
ORDER BY showtime.id ASC