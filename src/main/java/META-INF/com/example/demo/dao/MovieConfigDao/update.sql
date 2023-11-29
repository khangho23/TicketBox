UPDATE movieconfig 
SET 
startdate = /* movieConfig.startDate */'0',
enddate = /* movieConfig.endDate */'0',
createdate = /* movieConfig.createDate */'0',
updatedate = CURRENT_DATE,
status = 
        CASE
            WHEN CURRENT_DATE BETWEEN /* movieConfig.startDate */'0' AND /* movieConfig.endDate */'0' THEN 1
            WHEN CURRENT_DATE > /* movieConfig.endDate */'0' THEN 2
            WHEN CURRENT_DATE < /* movieConfig.startDate */'0' THEN 0
            ELSE null
        END
WHERE movieid =/*movieConfig.movieId*/'0' and branchid = /*movieConfig.branchId*/'0'
