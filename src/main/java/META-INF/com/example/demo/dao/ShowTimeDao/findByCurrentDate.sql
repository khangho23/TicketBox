select s.id,s.starttime, r."name" as branchName, m."name" || ' - ' || la."name"  as movieName, m.id as movieid
from showtime s left join languageofmovie l on s.languageofmovieid = l.id
				left join movie m on m.id = l.movieid
				left join "language" la on la.id = l.languageid
				left join room r on r.id = s.roomid
where s.showdate = '2023-10-22' and r.branchid = /* branchid */'cn2'
order by s.starttime