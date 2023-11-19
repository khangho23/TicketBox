SELECT ticket.id,
       CONCAT(seat.rowseat, seat.orderseat) AS seat,
       seatdetails.pricedetails AS seat_price,
       seat.pricecommon AS seat_price_common,
       seattype.name AS seattype,
       seattype.surchage seattype_surchage,
       showtime.starttime,
       showtime.showdate,
       movie.name AS moviename,
       customer.name AS customername,
       ticket.totalprice,
       ticket.createdate,
       ticket.vat
FROM ticket
         JOIN bill ON bill.id = ticket.billid
         JOIN seatdetails ON seatdetails.id = ticket.seatdetailsid
         JOIN seat ON seat.id = seatdetails.seatid
         JOIN seattype ON seattype.id = seat.seattypeid
         JOIN showtime ON showtime.id = ticket.showtimeid
		 JOIN languageofmovie ON languageofmovie.id = showtime.languageofmovieid
         JOIN movie ON movie.id = languageofmovie.movieid
		 JOIN language ON language.id = languageofmovie.languageid
         JOIN customer ON customer.id = bill.customerid
WHERE bill.id = /* billId */1