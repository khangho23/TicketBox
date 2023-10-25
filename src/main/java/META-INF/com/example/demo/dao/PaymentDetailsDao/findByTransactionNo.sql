SELECT
    id,
    paymethodid AS payMethodId,
    billid AS billId,
    vnp_transactionno AS vnp_TransactionNo,
    paystatus,
    amout
FROM paymentmethoddetails
WHERE vnp_transactionno = /* vnp_TransactionNo */'1'