SELECT distinct bill.id,
       bill.exportdate,
       bill.exportstatus,
       bill.qrcode,
       paymentmethod.name paymentmethod,
       starttime,
       showdate,
	   movie.poster,
       movie.name AS movie,
       movie.limitage,
       movie.yearofmanufacture,
	   language.name AS language,
       country.name AS country,
       room.name AS room,
       branch.name AS branch,
       branch.address AS branch_address,
       customer.name AS customer,
       customer.phone AS customer_phone,
       customer.email AS customer_email,
       STRING_AGG(DISTINCT CONCAT(seat.rowseat, seat.orderseat), ', ') AS seats,
       STRING_AGG(DISTINCT CONCAT(toppingdetails.quantity, topping.name), ', ') AS topping,
       (SELECT SUM(quantity) FROM toppingdetails WHERE billid = /* billId */162) AS topping_quantity,
       (SELECT SUM(pricewhenbuy * quantity) FROM toppingdetails WHERE billid = /* billId */162) AS topping_totalprice,
       (SELECT SUM(ticket.totalprice) FROM ticket WHERE billid = /* billId */162) AS ticket_totalprice,
       (SELECT SUM(totalprice * vat) FROM ticket WHERE billid = /* billId */162) AS ticket_vat,
       bill.totalprice AS total_price
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
	 left JOIN "language" ON "language".id = languageofmovie.languageid
         JOIN country ON movie.countryid = country.id
         LEFT JOIN toppingdetails ON bill.id = toppingdetails.billid
         LEFT JOIN toppingofbranch  ON toppingdetails.toppingofbranchid = toppingofbranch.id 
         left join topping on topping.id = toppingofbranch.toppingid 
WHERE ticket.billid = /* billId */162 AND customer.id = /* customerId */18
GROUP BY distinct bill.id,
         bill.exportdate,
         bill.exportstatus,
        bill.qrcode,
         paymentmethod.name,
         starttime,
         showdate,
	 movie.poster,
         movie.name,
         movie.limitage,
         movie.yearofmanufacture,
	 language.name,
         country.name,
         room.name,
         branch.name,
         branch.address,
         customer.name,
         customer.phone,
         customer.email,
         paymentmethoddetails.amout;