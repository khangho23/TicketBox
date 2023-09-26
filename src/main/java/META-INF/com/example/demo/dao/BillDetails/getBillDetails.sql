SELECT bill.id,
       bill.exportdate,
       bill.exportstatus,
       starttime,
       showdate,
       movie.name AS movie,
       movie.time AS movie_time,
       room.name AS room,
       branch.name AS branch,
       customer.name AS customer,
       customer.phone AS customer_phone,
       customer.email AS customer_email,
       STRING_AGG(DISTINCT CONCAT(seat.rowseat, seat.orderseat), ', ') AS seats,
       STRING_AGG(DISTINCT CONCAT(toppingdetails.quantity, topping.name), ', ') AS topping,
       SUM(toppingdetails.pricewhenbuy) AS topping_totalprice
FROM bill
         JOIN billdetails ON bill.id = billdetails.billid
         JOIN ticket ON ticket.id = billdetails.id
         JOIN customer ON ticket.customerid = customer.id
         JOIN seatdetails ON ticket.seatdetailsid = seatdetails.id
         JOIN seat ON seat.id = seatdetails.seatid
         JOIN room ON room.id = seatdetails.roomid
         JOIN branch ON room.branchid = branch.id
         JOIN showtime ON showtime.id = ticket.showtimeid
         JOIN movie ON movie.id = showtime.movieid
         LEFT JOIN toppingdetails ON bill.id = toppingdetails.billid
         LEFT JOIN topping ON toppingdetails.billid = bill.id
WHERE bill.id = /* customerId */9
GROUP BY bill.id,
         bill.exportdate,
         bill.exportstatus,
         starttime,
         showdate,
         movie.name,
         movie.time,
         room.name,
         branch.name,
         customer.name,
         customer.phone,
         customer.email;