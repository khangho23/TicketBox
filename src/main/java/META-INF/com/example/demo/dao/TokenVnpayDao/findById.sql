SELECT
    id,
    customerid,
    vnp_token,
    vnp_card_number,
    vnp_card_type,
    vnp_bank_code,
    vnp_create_date
FROM tokenvnpay
WHERE id = /* id */0