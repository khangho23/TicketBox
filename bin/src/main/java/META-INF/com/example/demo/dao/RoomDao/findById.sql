SELECT 
    room.id,
    room.branchid,
    room.name,
    branch.name as branchName
FROM room 
JOIN branch on branch.id = room.branchid
WHERE room.id = /* id */'PC01';