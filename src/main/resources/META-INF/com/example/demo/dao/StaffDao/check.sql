select 
	s.id,
	s.email,
	s.name,
	s.password,
	s.role,
	s.branchId,
    b.name as branchName
from staff s join branch b on b.id = s.branchId
where s.email = /* email */'duc@gmail.com'