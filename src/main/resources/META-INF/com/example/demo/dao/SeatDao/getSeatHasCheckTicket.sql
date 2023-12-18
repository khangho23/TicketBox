WITH seat_ordered AS (
    SELECT 
        ticket.billid AS ticketId,
        ticket.seatdetailsid,
        showtime.roomid,
        room.name as roomName
    FROM
        ticket JOIN showtime ON ticket.showtimeid = showtime.id
                JOIN room ON room.id = showtime.roomid
				JOIN bill ON bill.id = ticket.billid
    WHERE showtime.id = /* id */'341' AND bill.exportstatus = 1
    ORDER BY ticket.billid
)

SELECT 
		seat.rowseat AS Row,
        STRING_AGG (
            sdt.id || ':' || seat.rowseat || seat.orderseat::text || ':'||
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
WHERE sdt.roomid = (SELECT roomid FROM showtime WHERE id = /* id */'341')
GROUP BY seat.rowseat
ORDER BY seat.rowseat