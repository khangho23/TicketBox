SELECT movie.id, movie.name, yearofmanufacture,
	   poster, time, describe,
	   trailer, status, limitage,
	   country.name AS country,
       STRING_AGG(DISTINCT typeofmovie.name, ', ') AS movie_types,
       STRING_AGG(DISTINCT actor.name, ', ') AS actors,
	   STRING_AGG(DISTINCT director.name, ', ') AS directors,
	   STRING_AGG(DISTINCT language.name, ', ') AS languages
FROM movie
LEFT JOIN moviedetails ON movie.id = moviedetails.movieid
JOIN typeofmovie ON moviedetails.typeofmovieid = typeofmovie.id
JOIN actorofmovie ON movie.id = actorofmovie.movieid
JOIN actor ON actor.id = actorofmovie.actorid
JOIN languageofmovie ON languageofmovie.movieid = movie.id
JOIN language ON language.id = languageofmovie.languageid
JOIN directorofmovie ON directorofmovie.movieid = movie.id
JOIN director ON director.id = directorofmovie.directorid
JOIN country ON country.id = movie.countryid

WHERE movie.id = /* movieid */'MP01'
GROUP BY movie.id, movie.name, yearofmanufacture,
         poster, time, describe, trailer,
         limitage, country;