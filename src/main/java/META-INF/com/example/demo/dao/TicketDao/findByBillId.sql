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
       ticket.vat,
       ticket.exportstatus
FROM ticket
         JOIN billdetails ON billdetails.ticketid = ticket.id
         JOIN seatdetails ON seatdetails.id = ticket.seatdetailsid
         JOIN seat ON seat.id = seatdetails.seatid
         JOIN seattype ON seattype.id = seat.seattypeid
         JOIN showtime ON showtime.id = ticket.showtimeid
         JOIN movie ON movie.id = showtime.movieid
         JOIN customer ON customer.id = ticket.customerid
WHERE billdetails.billid = /* billId */1