SELECT bill.id,
       starttime,
       showdate,
       movie.name AS movie,
       branch.name AS branch,
       branch.address AS branch_address,
       customer.name AS customer,
       STRING_AGG(DISTINCT CONCAT(seat.rowseat, seat.orderseat), ', ') AS seats,
       STRING_AGG(DISTINCT CONCAT(toppingdetails.quantity, topping.name), ', ') AS topping,
       SUM(toppingdetails.pricewhenbuy) AS topping_totalprice,
       SUM(ticket.totalprice) AS ticket_totalprice,
       SUM(ticket.totalprice * ticket.vat) AS ticket_vat
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
         LEFT JOIN toppingdetails ON bill.id = toppingdetails.billid
         LEFT JOIN topping ON toppingdetails.billid = bill.id
WHERE ticket.billid = /* billId */1 AND customer.id = /* customerId */1
GROUP BY bill.id,
         starttime,
         showdate,
         movie.name,
         branch.name,
         branch.address,
         customer.name;