SELECT
	customer.name AS customername,
	showtime.showdate AS showdate,
	showtime.starttime AS starttime,
	seat.id AS seat,
	seattype.name AS seattype,
	totalprice,
	createdate,
	vat,
	exportstatus
FROM ticket
JOIN customer ON ticket.customerid = customer.id
JOIN showtime ON showtime.id = ticket.showtimeid
JOIN seatdetails ON seatdetails.id = showtime.id
JOIN seat ON seat.id = seatdetails.seatid
JOIN seattype ON seattype.id = seat.seattypeid

WHERE customerid = /* customerId */1;