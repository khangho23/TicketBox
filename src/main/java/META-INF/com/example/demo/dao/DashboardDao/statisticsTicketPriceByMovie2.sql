WITH months AS (
  SELECT generate_series(
    DATE (concat(/* year */'2023','-01-01')), 
    DATE (concat(/* year */'2023','-12-31')),  
    INTERVAL '1 day'
  ) AS day
)
SELECT 
    TO_CHAR(day, 'DD/MM') AS month,
    COALESCE(count(ticket.id), 0) AS totalTicket,
    COALESCE(sum(ticket.totalprice), 0) AS totalPrice
FROM ticket 
join showtime on ticket.showtimeid = showtime.id
join languageofmovie ON languageofmovie.id = showtime.languageofmovieid
join movie on movie.id = languageofmovie.movieid and movie.name= /* movieName */'NGƯỢC DÒNG THỜI GIAN ĐỂ YÊU ANH'
 JOIN months ON TO_CHAR(day, 'DD/MM') = TO_CHAR(showtime.showdate, 'DD/MM')
and EXTRACT(YEAR FROM showtime.showdate) = /* year */'2023' 
GROUP BY day
ORDER BY month;
