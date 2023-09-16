WITH seat_ordered AS (
    SELECT 
        ticket.id AS ticketId,
        ticket.seatdetailsid,
        showtime.roomid
    FROM
        ticket JOIN showtime ON ticket.showtimeid = showtime.id
    WHERE showtime.id = /* id */'1'
    ORDER BY ticket.id
)

SELECT sdt.id,
        CONCAT(seat.rowseat,seat.orderseat) AS name,
        seat.pricecommon,
        so.ticketId
FROM 
    seat_ordered so 
    RIGHT JOIN seatdetails AS sdt ON so.seatdetailsid = sdt.id
    JOIN seat ON seat.id = sdt.seatid
WHERE sdt.roomid = (SELECT roomid FROM showtime WHERE id = /* id */'1')