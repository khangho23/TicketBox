INSERT INTO movieconfig (movieid, branchid, startdate, enddate, createdate, updatedate, status)
SELECT 
    /* movieConfig.movieId */'0' as movieid,
    /* movieConfig.branchId */'0' as branchid,
    /* movieConfig.startDate */'0' as startdate,
    /* movieConfig.endDate */'0' as enddate,
    /* movieConfig.createDate */'0' as createdate,
    /* movieConfig.updateDate */'0' as updatedate,
    CASE
        WHEN CURRENT_DATE BETWEEN /* movieConfig.startDate */'0' AND /* movieConfig.endDate */'0' THEN 1
        WHEN CURRENT_DATE > /* movieConfig.endDate */'0' THEN 2
        WHEN CURRENT_DATE < /* movieConfig.startDate */'0' THEN 0
        ELSE null
    END as status;