UPDATE staff
SET 
    name = CASE WHEN name = /*staff.name*/'135' then name else /*staff.name*/'1234' end, 
    password = CASE WHEN password = /*staff.password*/'135' then password else /*staff.password*/'1234' end, 
    birthday = CASE WHEN birthday = /*staff.birthday*/'135' then birthday else /*staff.birthday*/'1234' end, 
    phone = CASE WHEN phone = /*staff.phone*/'135' then phone else /*staff.phone*/'1234' end, 
    email = CASE WHEN email = /*staff.email*/'135' then email else /*staff.email*/'1234' end, 
    gender = CASE WHEN gender = /*staff.gender*/'135' then gender else /*staff.gender*/'1234' end,
    role = CASE WHEN role = /*staff.role*/'135' then role else /*staff.role*/'1234' end 
where id = /* staff.id */'123'

