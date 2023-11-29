SELECT 
	movie.id as movieId,
	movie.name as movieName
FROM showtime
join languageofmovie ON languageofmovie.id = showtime.languageofmovieid
join movie on movie.id = languageofmovie.movieid
join room on room.id = showtime.roomid
JOIN branch on branch.id = room.branchid
where branch.id = CASE WHEN /* branchId */'cn1' = '0' THEN branch.id ELSE /* branchId */'cn1' END
GROUP BY movie.id, movie.name