WITH months AS (
  SELECT generate_series(
    DATE (concat(/* year */'2023','-01-01')), 
    DATE (concat(/* year */'2023','-12-31')), 
    INTERVAL '1 month'
  ) AS month
)
SELECT 
    TO_CHAR(month, 'MM') AS id,
    COALESCE(sum(ticket.totalprice), 0) AS totalPrice,
	COALESCE(count(ticket.id), 0) AS totalTicket
FROM ticket 
join bill on bill.id = ticket.billid
join showtime on ticket.showtimeid = showtime.id
join room on room.id = showtime.roomid
join branch on branch.id = room.branchid
right JOIN months ON TO_CHAR(month, 'MM') = TO_CHAR(bill.exportdate, 'MM')
and EXTRACT(YEAR FROM bill.exportdate) = /* year */'2023'
and branch.name =/* branchName */'Hưng Thịnh'
GROUP BY month
ORDER BY id;
