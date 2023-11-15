SELECT 
    COUNT(DISTINCT bill.id) AS total_bill_count
FROM 
    bill
JOIN 
    customer ON bill.customerid = customer.id
LEFT JOIN 
    ticket ON ticket.billid = bill.id
LEFT JOIN 
    showtime ON showtime.id = ticket.showtimeid
LEFT JOIN 
    languageofmovie ON languageofmovie.id = showtime.languageofmovieid
JOIN 
    movie ON languageofmovie.movieid = movie.id
WHERE 
    movie.id = /* id */'MP07' AND bill.rate IS NOT NULL;