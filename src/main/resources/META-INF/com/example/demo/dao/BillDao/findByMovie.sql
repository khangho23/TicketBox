SELECT bill.*
FROM bill 
    LEFT JOIN ticket ON bill.id = ticket.billid
    LEFT JOIN showtime ON showtime.id = ticket.showtimeid
    LEFT JOIN languageofmovie ON showtime.languageofmovieid = languageofmovie.id
WHERE languageofmovie.movieid = /*id*/'MP01'