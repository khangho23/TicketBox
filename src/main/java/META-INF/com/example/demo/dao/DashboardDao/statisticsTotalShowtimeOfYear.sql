WITH months AS (
  SELECT generate_series(
    DATE (concat(/* year */'2023','-01-01')), 
    DATE (concat(/* year */'2023','-12-31')), 
    INTERVAL '1 month'
  ) AS month
)
SELECT 
    TO_CHAR(month, 'MM') AS id,
    COALESCE(count(showtime.id), 0) AS totalShowtime
FROM showtime
join room on showtime.roomid=room.id
join branch on branch.id = room.branchid
right JOIN months ON TO_CHAR(month, 'MM') = TO_CHAR(showtime.showdate, 'MM')
and EXTRACT(YEAR FROM showtime.showdate) = /* year */'2023'
and branch.id =/* branchId */'cn2'
GROUP BY month
ORDER BY id;