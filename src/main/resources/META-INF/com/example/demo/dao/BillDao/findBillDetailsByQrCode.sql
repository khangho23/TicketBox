SELECT bill.id,
       bill.exportdate,
       bill.exportstatus,
       paymentmethod.name paymentmethod,
       starttime,
       showdate,
       movie.name AS movie,
       movie.limitage,
       movie.yearofmanufacture,
	   "language".name AS "language",
       country.name AS country,
       room.name AS room,
       branch.name AS branch,
       branch.address AS branch_address,
       customer.name AS customer,
       customer.phone AS customer_phone,
       customer.email AS customer_email,
       SUM(ticket.totalprice) * ticket.vat AS ticket_vat,
       STRING_AGG(DISTINCT CONCAT(seat.rowseat, seat.orderseat), ', ') AS seats,
       STRING_AGG(DISTINCT CONCAT(toppingdetails.quantity, topping.name), ', ') AS topping,
       SUM(toppingdetails.pricewhenbuy * toppingdetails.quantity) AS topping_totalprice,
       SUM(ticket.totalprice) AS ticket_totalprice,
       bill.totalprice AS total_price,
	   bill.qrcode,
	   movie.id as poster
FROM bill
         LEFT JOIN paymentmethoddetails ON paymentmethoddetails.billid = bill.id
         LEFT JOIN paymentmethod ON paymentmethod.id = paymentmethoddetails.paymethodid
         left JOIN ticket ON ticket.billid = bill.id
         JOIN customer ON customer.id = bill.customerid
         JOIN seatdetails ON ticket.seatdetailsid = seatdetails.id
         JOIN seat ON seat.id = seatdetails.seatid
         JOIN room ON room.id = seatdetails.roomid
         JOIN branch ON room.branchid = branch.id
         JOIN showtime ON showtime.id = ticket.showtimeid and showtime.roomid = room.id 
         JOIN languageofmovie ON languageofmovie.id = showtime.languageofmovieid
        JOIN movie ON movie.id = languageofmovie.movieid
        LEFT JOIN "language" ON "language".id = languageofmovie.languageid
         JOIN country ON movie.countryid = country.id
         LEFT JOIN toppingdetails ON bill.id = toppingdetails.billid
         LEFT JOIN toppingofbranch  ON toppingdetails.toppingofbranchid = toppingofbranch.id 
         LEFT join topping on topping.id = toppingofbranch.toppingid 
WHERE bill.qrcode = /* qrCode */'gggggggggggggggggggggggggggggggg'
GROUP BY bill.id,
         bill.exportdate,
         bill.exportstatus,
         paymentmethod.name,
         starttime,
         showdate,
         movie.name,
         movie.limitage,
         movie.yearofmanufacture,
		 "language".name,
         country.name,
         room.name,
         branch.name,
         branch.address,
         customer.name,
         customer.phone,
         customer.email,
		bill.qrcode,
        ticket.vat,
		movie.id ;