SELECT
    id,
    name,
    year_of_manufacture,
    image,
    director,
    national,
    time,
    describe,
    trailer
FROM movie
WHERE id IN (SELECT id FROM movie_details WHERE movie_type_id = /* movieTypeId */'LP01')