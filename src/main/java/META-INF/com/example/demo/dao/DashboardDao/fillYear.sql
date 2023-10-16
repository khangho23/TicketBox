select 
	to_char(createdate,'yyyy') as year 
from ticket
group by year