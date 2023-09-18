WITH seat_ordered AS (
    SELECT 
        ticket.id AS ticketId,
        ticket.seatdetailsid,
        showtime.roomid,
        room.name as roomName
    FROM
        ticket JOIN showtime ON ticket.showtimeid = showtime.id
                JOIN room ON room.id = showtime.roomid
    WHERE showtime.id = /* id */'1'
    ORDER BY ticket.id
)

SELECT seat.rowseat AS Row,
        STRING_AGG (
            seat.rowseat|| seat.orderseat::text || ':'||
            CASE 
                WHEN
                    so.ticketId IS NULL
                THEN 
                    'false'
                ELSE
                    'true'
            END,','
        
        ) AS seats
FROM 
    seat_ordered so 
    RIGHT JOIN seatdetails AS sdt ON so.seatdetailsid = sdt.id
    JOIN seat ON seat.id = sdt.seatid
WHERE sdt.roomid = (SELECT roomid FROM showtime WHERE id = /* id */'1')
GROUP BY seat.rowseat
ORDER BY seat.rowseat