SELECT bill.rate,
	bill.review,
	bill.exportdate,
	customer.name
from bill
JOIN customer on bill.customerid = customer.id
LEFT JOIN ticket on ticket.billid = bill.id
LEFT JOIN showtime on showtime.id = ticket.showtimeid
LEFT JOIN languageofmovie on languageofmovie.id = showtime.languageofmovieid
JOIN movie on languageofmovie.movieid = movie.id
WHERE movie.id = /* id */'MP07'
GROUP BY bill.id, customer.name