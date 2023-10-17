SELECT 
    languageofmovie.id,
    languageofmovie.movieid,
    languageofmovie.languageid,
    movie.name as movieName,
    language.name as languageName
FROM languageofmovie
JOIN language on language.id = languageofmovie.languageid
JOIN movie on movie.id = languageofmovie.movieid