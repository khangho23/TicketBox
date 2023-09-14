SELECT
    sdt.id AS stt,
    s.id,
    s.seattypeid,
    s.rowseat,
    s.orderseat,
    s.pricecommon,
    sdt.roomid,
    sdt.pricedetails
FROM seat s JOIN seatdetails sdt ON s.id = sdt.seatid
WHERE sdt.roomid =  'PC01';