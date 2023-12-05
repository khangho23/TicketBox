select s.id,s.starttime, r."name" as branchName, m."name" || ' - ' || la."name"  as movieName, m.id as movieid, count(t.showtimeid) as ticketNumber
from showtime s left join languageofmovie l on s.languageofmovieid = l.id
				left join movie m on m.id = l.movieid
				left join "language" la on la.id = l.languageid
				left join room r on r.id = s.roomid
				left join ticket t on t.showtimeid = s.id
where s.showdate = '2023-10-01' and r.branchid = /* branchid */'cn1'
group by s.id,s.starttime, r.name, m.name, la.name , m.id
order by s.starttime