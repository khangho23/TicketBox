SELECT 
	movie.name as movieName
FROM showtime
join languageofmovie ON languageofmovie.id = showtime.languageofmovieid
join movie on movie.id = languageofmovie.movieid
join room on room.id = showtime.roomid
JOIN branch on branch.id = room.branchid
where branch.id =/* branchId */'cn2'
GROUP BY movieName