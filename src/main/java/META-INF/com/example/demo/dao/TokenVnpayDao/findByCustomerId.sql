SELECT
    id,
    customerid AS customerId,
    vnp_token,
    vnp_card_number
FROM tokenvnpay
WHERE customerid = /* customerId */0