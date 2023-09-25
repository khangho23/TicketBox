SELECT bill.id,
       bill.exportstatus,
       starttime,
       showdate,
       movie.name AS movie,
       movie.time,
       room.name AS room,
       branch.name AS branch,
       customer.address,
       STRING_AGG(DISTINCT CONCAT(seat.rowseat, seat.orderseat), ', ') AS seat
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
WHERE customer.id = /* customerId */1
GROUP BY bill.id,
         bill.exportstatus,
         starttime,
         showdate,
         movie.name,
         movie.time,
         room.name,
         branch.name,
         customer.address;