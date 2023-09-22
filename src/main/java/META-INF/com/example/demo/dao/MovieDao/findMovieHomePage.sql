SELECT  movie.id , movie.name, movie.poster, movie.time, movie.yearofmanufacture
FROM movie
LEFT JOIN country ON country.id = movie.countryid 
LEFT JOIN moviedetails ON movie.id = moviedetails.movieid 
LEFT JOIN typeofmovie ON moviedetails.typeofmovieid = typeofmovie.id 
LEFT JOIN showtime ON movie.id = showtime.movieid 
LEFT JOIN room ON room.id = showtime.roomid 
LEFT JOIN branch ON branch.id = room.branchid 

WHERE 
/*%if branchid != null */
    branch.id = /* branchid */'cn1'
/*%end*/ 
/*%if countryid != 0 */
    AND 
    country.id = /* countryid */1
/*%end*/
/*%if typeofmovieid != null */
    AND 
    typeofmovie.id = /* typeofmovieid */'LP01'
/*%end*/
AND movie.status = /* status */'0'
GROUP BY movie.id , movie.name, movie.poster, movie.time, movie.yearofmanufacture