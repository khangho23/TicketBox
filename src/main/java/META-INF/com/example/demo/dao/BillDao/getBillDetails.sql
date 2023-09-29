SELECT bill.id,
       bill.exportdate,
       bill.exportstatus,
       paymentmethod.name paymentmethod,
       starttime,
       showdate,
       movie.name AS movie,
       movie.limitage,
       movie.yearofmanufacture,
       country.name AS country,
       room.name AS room,
       branch.name AS branch,
       branch.address AS branch_address,
       customer.name AS customer,
       customer.phone AS customer_phone,
       customer.email AS customer_email,
       STRING_AGG(DISTINCT CONCAT(seat.rowseat, seat.orderseat), ', ') AS seats,
       STRING_AGG(DISTINCT CONCAT(toppingdetails.quantity, topping.name), ', ') AS topping,
       SUM(toppingdetails.pricewhenbuy) AS topping_totalprice,
       SUM(ticket.totalprice) AS ticket_totalprice
FROM bill
         LEFT JOIN paymentmethoddetails ON paymentmethoddetails.billid = bill.id
         LEFT JOIN paymentmethod ON paymentmethod.id = paymentmethoddetails.paymethodid
         JOIN billdetails ON bill.id = billdetails.billid
         JOIN ticket ON ticket.id = billdetails.id
         JOIN customer ON ticket.customerid = customer.id
         JOIN seatdetails ON ticket.seatdetailsid = seatdetails.id
         JOIN seat ON seat.id = seatdetails.seatid
         JOIN room ON room.id = seatdetails.roomid
         JOIN branch ON room.branchid = branch.id
         JOIN showtime ON showtime.id = ticket.showtimeid
         JOIN movie ON movie.id = showtime.movieid
         JOIN country ON movie.countryid = country.id
         LEFT JOIN toppingdetails ON bill.id = toppingdetails.billid
         LEFT JOIN topping ON toppingdetails.billid = bill.id
WHERE billdetails.billid = /* billId */1 AND ticket.customerid = /* customerId */1
GROUP BY bill.id,
         bill.exportdate,
         bill.exportstatus,
         paymentmethod.name,
         starttime,
         showdate,
         movie.name,
         movie.limitage,
         movie.yearofmanufacture,
         country.name,
         room.name,
         branch.name,
         branch.address,
         customer.name,
         customer.phone,
         customer.email;