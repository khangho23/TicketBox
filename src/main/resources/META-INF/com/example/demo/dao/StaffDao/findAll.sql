SELECT 
    staff.id, 
    staff.branchId,
    branch.name as branchName, 
    staff.name, 
    staff.password, 
    staff.birthday,
    TO_CHAR(staff.birthday, 'DD/MM/YYYY') as formatted_birthday, 
    staff.phone, 
    staff.email, 
    staff.gender,
    CASE 
        WHEN staff.gender = true THEN 'Nam'
        ELSE 'Nữ'
    END AS formatted_gender,
    staff.role,
    CASE 
        WHEN staff.role = 1 THEN 'Nhân viên'
        ELSE 'Quản lý'
    END AS formatted_role
FROM 
    staff
LEFT JOIN 
    branch ON staff.branchid = branch.id
WHERE 
	staff.status = true
ORDER BY 
    staff.id;