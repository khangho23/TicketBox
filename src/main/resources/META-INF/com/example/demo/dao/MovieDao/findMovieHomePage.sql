SELECT  movie.id , movie.name, movie.poster, movie.time, movie.yearofmanufacture
FROM movie
LEFT JOIN country ON country.id = movie.countryid 
LEFT JOIN moviedetails ON movie.id = moviedetails.movieid 
LEFT JOIN typeofmovie ON moviedetails.typeofmovieid = typeofmovie.id 
LEFT JOIN languageofmovie on languageofmovie.movieid = movie.id
JOIN movieconfig ON movie.id= movieconfig.movieid
JOIN branch ON branch.id=movieconfig.branchid

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
/*%if status == "0" */
and movie.id not in (select movieconfig.movieid from movieconfig where movieconfig.movieid = movie.id and movieconfig.status = '1') 
/*%end*/
AND movieconfig.status = /* status */'0'
GROUP BY movie.id , movie.name, movie.poster, movie.time, movie.yearofmanufacture