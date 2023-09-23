SELECT 
	movie.name as movieName,
	to_char(ticket.createdate,'YYYY') as year,
 	COALESCE(sum(ticket.totalprice),0) AS totalPrice,
	count(ticket.id) as totalTicket
FROM ticket 
JOIN showtime on showtime.id = ticket.showtimeid
JOIN movie ON movie.id = showtime.movieid
JOIN room ON room.id = showtime.roomid
JOIN branch ON branch.id = room.branchid
group by movieName,year