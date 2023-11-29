SELECT 
	s3.id as stt,
	r.id as roomId,
	s.rowseat || s.orderseat AS name, 
	s.pricecommon+s2.surchage+s4.price AS total
FROM seat s LEFT JOIN seattype s2 ON s.seattypeid = s2.id
			LEFT JOIN seatdetails s3 ON s.id =s3.seatid  
			LEFT JOIN room r ON s3.roomid =r.id 
			LEFT JOIN showtime s4 ON s4.roomid = r.id
WHERE s4.id =/*showtimeid*/'1' AND s.rowseat || s.orderseat = /*name*/'1'
