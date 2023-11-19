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
join bill on bill.id = ticket.billid
join showtime on ticket.showtimeid = showtime.id
join languageofmovie ON languageofmovie.id = showtime.languageofmovieid
join movie on movie.id = languageofmovie.movieid
JOIN room ON room.id = showtime.roomid
JOIN branch ON branch.id = room.branchid
 JOIN months ON TO_CHAR(day, 'DD/MM') = TO_CHAR(bill.exportdate, 'DD/MM')
and EXTRACT(YEAR FROM bill.exportdate) = /* year */'2023' 
where movie.name= /* movieName */'NGƯỢC DÒNG THỜI GIAN ĐỂ YÊU ANH' and branch.name=/*branchName*/'Hưng Thịnh'
GROUP BY day
ORDER BY month;
