select 
	to_char(exportdate,'yyyy') as year 
from bill
group by year