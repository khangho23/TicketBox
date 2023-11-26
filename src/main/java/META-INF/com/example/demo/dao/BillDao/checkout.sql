SELECT movie.poster,
       bill.id,
       starttime,
       showdate,
       movie.name AS movie,
       movie.status AS movie_status,
       branch.name AS branch,
       branch.address AS branch_address,
       customer.name AS customer,
       bill.totalprice AS totalprice,
       STRING_AGG(DISTINCT CONCAT(seat.rowseat, seat.orderseat), ', ') AS seats,
       STRING_AGG(DISTINCT CONCAT(toppingdetails.quantity, topping.name), ', ') AS topping,
	   (SELECT SUM(totalprice) FROM ticket
       	WHERE billid = /* billId */0) AS ticket_totalprice,
       SUM(DISTINCT ticket.totalprice * ticket.vat) AS ticket_vat,
	   SUM(toppingdetails.pricewhenbuy) AS topping_totalprice
FROM bill
JOIN ticket ON ticket.billid = bill.id
JOIN customer ON bill.customerid = customer.id
JOIN seatdetails ON ticket.seatdetailsid = seatdetails.id
JOIN seat ON seat.id = seatdetails.seatid
JOIN room ON room.id = seatdetails.roomid
JOIN branch ON room.branchid = branch.id
JOIN showtime ON showtime.id = ticket.showtimeid
JOIN languageofmovie ON languageofmovie.id = showtime.languageofmovieid
JOIN movie ON movie.id = languageofmovie.movieid
LEFT JOIN toppingdetails ON toppingdetails.billid = bill.id 
LEFT JOIN toppingofbranch ON toppingofbranch.id = toppingdetails.toppingofbranchid
LEFT JOIN topping ON topping.id = toppingofbranch.toppingid
WHERE ticket.billid = /* billId */0 AND customer.id = /* customerId */0
GROUP BY movie.poster,
         bill.id,
         starttime,
         showdate,
         movie.name,
         movie.status,
         branch.name,
         branch.address,
         customer.name;