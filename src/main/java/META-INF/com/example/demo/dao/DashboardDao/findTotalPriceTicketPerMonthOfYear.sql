WITH months AS (
  SELECT generate_series(
    DATE '2023-01-01', 
    DATE '2023-12-31', 
    INTERVAL '1 month'
  ) AS month
)
SELECT 
    TO_CHAR(month, 'MM') AS id,
    COALESCE(sum(ticket.totalprice), 0) AS totalPrice,
	COALESCE(count(ticket.id), 0) AS totalTicket
FROM ticket 
join showtime on ticket.showtimeid = showtime.id
right JOIN months ON TO_CHAR(month, 'MM') = TO_CHAR(showtime.showdate, 'MM')
and EXTRACT(YEAR FROM showtime.showdate) = /* year */'2023'
GROUP BY month
ORDER BY id;
