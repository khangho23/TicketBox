SELECT bill.rate,
	bill.review,
	TO_CHAR(bill.exportdate, 'HH24:MI:SS DD-MM-YYYY') as exportdate,
	customer.name
from bill
JOIN customer on bill.customerid = customer.id
LEFT JOIN ticket on ticket.billid = bill.id
LEFT JOIN showtime on showtime.id = ticket.showtimeid
LEFT JOIN languageofmovie on languageofmovie.id = showtime.languageofmovieid
JOIN movie on languageofmovie.movieid = movie.id
WHERE movie.id = /* id */'MP07' AND bill.rate != 0
GROUP BY bill.id, customer.name
ORDER BY bill.id DESC 
LIMIT CAST(/* pageSize */4 AS INT) OFFSET (CAST(/* page */1 AS INT) - 1) * CAST(/* pageSize */4 AS INT)
