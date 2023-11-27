SELECT room.*, CONCAT(branch.name, ' - ' ,branch.address) AS branchname
FROM room JOIN branch ON room.branchid = branch.id
WHERE room.branchid = /* branchid */'cn1'