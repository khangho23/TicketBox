SELECT TO_CHAR(showtime.starttime, 'HH24:MI:SS') as starttime, (case when test.sl is not null then test.sl else 0 end) as quantity
FROM showtime LEFT JOIN (SELECT showtimeid, COUNT(ticket.id) as sl FROM ticket group by showtimeid) as test on test.showtimeid = showtime.id
JOIN languageofmovie ON languageofmovie.id = showtime.languageofmovieid
JOIN room on room.id = showtime.roomid
WHERE showtime.showdate = CURRENT_DATE
AND (languageofmovie.movieid = CASE WHEN /* movieId */'MP07' = '0' THEN languageofmovie.movieid ELSE /* movieId */'MP07' END)
AND (room.branchid = CASE WHEN /* branchId */'cn1' = '0' THEN room.branchid ELSE /* branchId */'cn1' END)
ORDER BY starttime