SELECT 
	toppingofbranch.id as toppingofbranchid,
	branchid,
	toppingid as id,
	topping.name,
	topping.logo,
	quantity,
	price
FROM topping Left join toppingofbranch on topping.id = toppingofbranch.toppingid where branchid=/* branchid */'cn1';