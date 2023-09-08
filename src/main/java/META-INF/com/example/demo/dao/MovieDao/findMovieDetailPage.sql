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
LEFT JOIN typeofmovie ON moviedetails.typeofmovieid = typeofmovie.id
LEFT JOIN actorofmovie ON movie.id = actorofmovie.movieid
LEFT JOIN actor ON actor.id = actorofmovie.actorid
LEFT JOIN languageofmovie ON languageofmovie.movieid = movie.id
LEFT JOIN language ON language.id = languageofmovie.languageid
LEFT JOIN directorofmovie ON directorofmovie.movieid = movie.id
LEFT JOIN director ON director.id = directorofmovie.directorid
LEFT JOIN country ON country.id = movie.countryid

WHERE movie.id = /* movieid */'MP01'
GROUP BY movie.id, movie.name, yearofmanufacture,
         poster, time, describe, trailer,
         status, limitage, country;
