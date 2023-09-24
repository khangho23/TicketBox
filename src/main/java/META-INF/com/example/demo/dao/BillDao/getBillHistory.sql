SELECT bill.id,
       bill.totalprice,
       exportstatus,
       starttime,
       showdate,
       movie.name                                        AS movie,
       movie.time,
       room.name                                         AS room,
       branch.name                                          branch,
       address,
       STRING_AGG(CONCAT(seat.row, seat.orderseat), ',') AS seats,
FROM bill
         JOIN billdetails ON bill.id = billdetails.billid
         JOIN ticket ON ticket.id = billdetails.id
         JOIN showtime ON showtime.id = ticket.showtimeid
         JOIN movie ON movie.id = showtime.movieid

WHERE customer.id = /* customerId */1
GROUP BY bill.id,
         bill.totalprice,
         exportstatus,
         starttime,
         showdate,
         movie.name,
         movie.time,
         room.name,
         branch.name,
         address;