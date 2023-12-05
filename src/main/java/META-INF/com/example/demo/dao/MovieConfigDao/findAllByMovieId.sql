select 
	movieconfig.id,
	/* movieId */'MP01' as movieId,
	branch.id as branchId,
	movieconfig.startdate,
	movieconfig.enddate,
	movieconfig.createdate,
	movieconfig.updatedate
from movieconfig
right join branch on branch.id = movieconfig.branchid and movieconfig.movieid  = /* movieId */'MP01'