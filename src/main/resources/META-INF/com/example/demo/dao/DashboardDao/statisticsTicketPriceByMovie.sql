SELECT 
	movie.name as movieName,
	to_char(bill.exportdate,'YYYY') as year,
 	COALESCE(sum(ticket.totalprice),0) AS totalPrice,
	count(ticket.id) as totalTicket
FROM ticket 
JOIN bill on bill.id = ticket.billid
JOIN showtime on showtime.id = ticket.showtimeid
JOIN languageofmovie ON languageofmovie.id = showtime.languageofmovieid
JOIN movie ON movie.id = languageofmovie.movieid
JOIN room ON room.id = showtime.roomid
JOIN branch ON branch.id = room.branchid
where branch.id =/* branchId */'cn1'
group by movieName,year