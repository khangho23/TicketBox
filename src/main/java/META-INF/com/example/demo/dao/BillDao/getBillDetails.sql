WITH TotalTicketPrice AS (
    SELECT billid, SUM(totalprice) AS total_price
    FROM ticket
    WHERE billid = /* billId */139
    GROUP BY billid
)

SELECT 
    bill.id,
    bill.exportdate,
    bill.exportstatus,
    bill.qrcode,
    paymentmethod.name AS paymentmethod,
    starttime,
    showdate,
    movie.poster,
    movie.name AS movie,
    movie.limitage,
    movie.yearofmanufacture,
    language.name AS language,
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
    TTP.total_price AS ticket_totalprice
FROM 
    bill
LEFT JOIN 
    paymentmethoddetails ON paymentmethoddetails.billid = bill.id
LEFT JOIN 
    paymentmethod ON paymentmethod.id = paymentmethoddetails.paymethodid
JOIN 
    ticket ON ticket.billid = bill.id
JOIN 
    customer ON customer.id = bill.customerid
JOIN 
    seatdetails ON ticket.seatdetailsid = seatdetails.id
JOIN 
    seat ON seat.id = seatdetails.seatid
JOIN 
    room ON room.id = seatdetails.roomid
JOIN 
    branch ON room.branchid = branch.id
JOIN 
    showtime ON showtime.id = ticket.showtimeid
JOIN 
    languageofmovie ON languageofmovie.id = showtime.languageofmovieid
JOIN 
    movie ON movie.id = languageofmovie.movieid
JOIN 
    "language" ON "language".id = languageofmovie.languageid
JOIN 
    country ON movie.countryid = country.id
LEFT JOIN 
    toppingdetails ON bill.id = toppingdetails.billid
LEFT JOIN 
    topping ON toppingdetails.billid = bill.id
LEFT JOIN 
    TotalTicketPrice TTP ON TTP.billid = bill.id
WHERE 
    ticket.billid = /* billId */139 AND customer.id = /* customerId */24
GROUP BY 
    bill.id,
    bill.exportdate,
    bill.exportstatus,
    bill.qrcode,
    paymentmethod.name,
    starttime,
    showdate,
    movie.poster,
    movie.name,
    movie.limitage,
    movie.yearofmanufacture,
    language.name,
    country.name,
    room.name,
    branch.name,
    branch.address,
    customer.name,
    customer.phone,
    customer.email,
    TTP.total_price;