SELECT movie.id,
    movie.poster
FROM movie 
LEFT JOIN moviedetails ON movie.id = moviedetails.movieid
WHERE moviedetails.typeofmovieid IN /* typeofmovieid */('LP01','LP02')
GROUP BY movie.id
LIMIT 10