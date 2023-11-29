select 
	id,
	movieId,
	branchId,
	startDate,
	endDate,
	createDate,
	updateDate
from movieconfig 
where movieid = /*movieId*/'MP01' and branchid=/*branchId*/'cn1'