SELECT COUNT(DISTINCT bill.id) AS total_bill_count
FROM bill
         JOIN ticket ON ticket.billid = bill.id
         JOIN customer ON customer.id = bill.customerid
         JOIN seatdetails ON ticket.seatdetailsid = seatdetails.id
         JOIN seat ON seat.id = seatdetails.seatid
         JOIN room ON room.id = seatdetails.roomid
         JOIN branch ON room.branchid = branch.id
         JOIN showtime ON showtime.id = ticket.showtimeid
		 JOIN languageofmovie ON languageofmovie.id = showtime.languageofmovieid
         JOIN movie ON movie.id = languageofmovie.movieid
		 JOIN language ON language.id = languageofmovie.languageid
WHERE customer.id = /* customerId */1