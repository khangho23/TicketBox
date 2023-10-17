WITH months AS (
  SELECT generate_series(
    DATE '2023-01-01', 
    DATE '2023-12-31', 
    INTERVAL '1 month'
  ) AS month
)
SELECT 
    TO_CHAR(month, 'MM') AS id,
    COALESCE(count(ticket.id), 0) AS totalTicket,
    COALESCE(sum(ticket.totalprice), 0) AS totalPrice
FROM ticket 
join showtime on ticket.showtimeid = showtime.id
join movie on movie.id = showtime.movieid and movie.name= /* movieName */'NGƯỢC DÒNG THỜI GIAN ĐỂ YÊU ANH'
right JOIN months ON TO_CHAR(month, 'MM') = TO_CHAR(showtime.showdate, 'MM')
and EXTRACT(YEAR FROM showtime.showdate) = /* year */'2023'
GROUP BY month
ORDER BY id;
