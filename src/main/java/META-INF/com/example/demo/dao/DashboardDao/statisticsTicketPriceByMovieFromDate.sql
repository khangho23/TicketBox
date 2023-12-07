SELECT 
	TO_CHAR(bill.exportdate, 'YYYY-MM-DD') as date,
    COALESCE(count(ticket.id), 0) AS totalTicket,
    COALESCE(sum(ticket.totalprice), 0) AS totalPrice
FROM ticket
join bill ON bill.id = ticket.billid
join showtime on ticket.showtimeid = showtime.id
join languageofmovie ON languageofmovie.id = showtime.languageofmovieid
join movie on movie.id = languageofmovie.movieid
join room on room.id = showtime.roomid
JOIN branch on branch.id = room.branchid
where TO_CHAR(bill.exportdate, 'YYYY-MM-DD') >= /* startDate */'2023-05-01' and TO_CHAR(bill.exportdate, 'YYYY-MM-DD') <= /* endDate */'2023-11-01'
/*%if movieId.length() > 0*/
    and movie.id = /* movieId */'MP01'
/*%end*/
and branch.id =/* branchId */'cn2'
GROUP BY date
order by date asc