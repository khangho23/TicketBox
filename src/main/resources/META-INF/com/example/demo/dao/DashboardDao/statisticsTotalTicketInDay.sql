SELECT TO_CHAR(showtime.starttime, 'HH24:MI:SS') as starttime,
  COALESCE(SUM(test.sl), 0) as quantity
FROM showtime
LEFT JOIN (
  SELECT showtimeid, COUNT(ticket.id) as sl
  FROM ticket
  GROUP BY showtimeid
) as test ON test.showtimeid = showtime.id
JOIN languageofmovie ON languageofmovie.id = showtime.languageofmovieid
JOIN room ON room.id = showtime.roomid
WHERE showtime.showdate = CURRENT_DATE
  AND (languageofmovie.movieid = CASE WHEN /* movieId */'MP07' = '0' THEN languageofmovie.movieid ELSE /* movieId */'MP07' END)
  AND (room.branchid = CASE WHEN /* branchId */'cn1' = '0' THEN room.branchid ELSE /* branchId */'cn1' END)
GROUP BY starttime
ORDER BY starttime;