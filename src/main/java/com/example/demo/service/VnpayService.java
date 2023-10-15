package com.example.demo.service;

import com.example.demo.admin.controller.enums.RequestStatusEnum;
import com.example.demo.config.VnpayConfig;
import com.example.demo.dto.VnpayPaymentDto;
import com.example.demo.dto.VnpayToken;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.util.PaymentUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VnpayService {
    private final int REMOVE_DECIMAL_DIGITS = 100;
    private final String BILL_PAYMENT = "250000";
    private final int TIME_OUT = 20;

    @Autowired
    PaymentUtils paymentUtils;

    public String createPayment(HttpServletRequest request, VnpayPaymentDto vnpayPaymentDto) throws InvalidRequestParameterException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String content =  paymentUtils.validateBankTransferContent(vnpayPaymentDto.getVnp_OrderInfo());

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VnpayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VnpayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", VnpayConfig.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(Integer.parseInt(vnpayPaymentDto.getVnp_Amount()) * REMOVE_DECIMAL_DIGITS));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", vnpayPaymentDto.getVnp_BankCode());
        vnp_Params.put("vnp_TxnRef", VnpayConfig.getRandomNumber(8));
        vnp_Params.put("vnp_OrderInfo" , content);
        vnp_Params.put("vnp_OrderType", BILL_PAYMENT);
        vnp_Params.put("vnp_Locale", vnpayPaymentDto.getVnp_Locale());
        vnp_Params.put("vnp_ReturnUrl", VnpayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", VnpayConfig.getIpAddress(request));

        paymentInterval(vnp_Params);

        String[] queryAndHashData = buildQueryUrl(vnp_Params);
        String queryUrl = queryAndHashData[0];
        String hashData = queryAndHashData[1];

        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, hashData);
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;

//        String paymentUrl = VnpayConfig.vnp_PayUrl + "?" + queryUrl;
//        vnp_Params.put("redirect_url", paymentUrl);

        return VnpayConfig.vnp_PayUrl + "?" + queryUrl;
    }

    public String createToken(HttpServletRequest request, VnpayToken vnpayToken) throws InvalidRequestParameterException {
        String content =  paymentUtils.validateBankTransferContent(vnpayToken.getVnp_txn_desc());

        Map<String, String> vnp_Params = new HashMap<>();

        vnp_Params.put("vnp_version", VnpayConfig.vnp_Version);
        vnp_Params.put("vnp_command", "token_create");
        vnp_Params.put("vnp_tmn_code", VnpayConfig.vnp_TmnCode);
        vnp_Params.put("vnp_app_user_id", vnpayToken.getVnp_app_user_id());
        vnp_Params.put("vnp_card_type", vnpayToken.getVnp_card_type());
        vnp_Params.put("vnp_bank_code", vnpayToken.getVnp_bank_code());
        vnp_Params.put("vnp_locale", vnpayToken.getVnp_locale());
        vnp_Params.put("vnp_txn_ref", VnpayConfig.getRandomNumber(8));
        vnp_Params.put("vnp_txn_desc", content);
        vnp_Params.put("vnp_curr_code", "VND");
        vnp_Params.put("vnp_return_url", VnpayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_cancel_url", VnpayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_ip_addr", "192.168.2.41");

        currentPaymentTime(vnp_Params);

        String[] queryAndHashData = buildQueryUrl(vnp_Params);
        String queryUrl = queryAndHashData[0];
        String hashData = queryAndHashData[1];

        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, hashData);
        queryUrl += "&vnp_secure_hash=" + vnp_SecureHash;

        return VnpayConfig.vnp_CreateToken + "?" + queryUrl;
    }

//    public VnpayToken getToken(HttpServletRequest request, VnpayToken vnpayToken) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
//        Map<String, String> vnp_Params = new HashMap<>();
//
//        vnp_Params.put("vnp_app_user_id", vnpayToken.getVnp_app_user_id());
//        vnp_Params.put("vnp_card_type", vnpayToken.getVnp_card_type());
//        vnp_Params.put("vnp_bank_code", vnpayToken.getVnp_bank_code());
//        vnp_Params.put("vnp_locale", vnpayToken.getVnp_locale());
//
//        return vnpayToken;
//    }

    public String paymentAndCreateToken(HttpServletRequest request, VnpayToken vnpayToken) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InvalidRequestParameterException {
        String content =  paymentUtils.validateBankTransferContent(vnpayToken.getVnp_txn_desc());

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_version", VnpayConfig.vnp_Version);
        vnp_Params.put("vnp_command", "pay_and_create");
        vnp_Params.put("vnp_tmn_code", VnpayConfig.vnp_TmnCode);
        vnp_Params.put("vnp_app_user_id", vnpayToken.getVnp_app_user_id());
        vnp_Params.put("vnp_bank_code", vnpayToken.getVnp_bank_code());
        vnp_Params.put("vnp_locale", vnpayToken.getVnp_locale());
        vnp_Params.put("vnp_card_type", vnpayToken.getVnp_card_type());
        vnp_Params.put("vnp_txn_ref", VnpayConfig.getRandomNumber(8));
        vnp_Params.put("vnp_txn_desc", content);
        vnp_Params.put("vnp_amount", String.valueOf(Integer.parseInt(vnpayToken.getVnp_amount()) * REMOVE_DECIMAL_DIGITS));
        vnp_Params.put("vnp_curr_code", "VND");
        vnp_Params.put("vnp_return_url", VnpayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_cancel_url", VnpayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_ip_addr", "192.168.2.41");
        vnp_Params.put("vnp_store_token", "1");

        currentPaymentTime(vnp_Params);

        String[] queryAndHashData = buildQueryUrl(vnp_Params);
        String queryUrl = queryAndHashData[0];
        String hashData = queryAndHashData[1];

        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, hashData);
        queryUrl += "&vnp_secure_hash=" + vnp_SecureHash;

        return VnpayConfig.vnp_PaymentAndCreateToken + "?" + queryUrl;
    }

    public String paymentByToken(HttpServletRequest request, VnpayToken vnpayToken) throws InvalidRequestParameterException {
        String content =  paymentUtils.validateBankTransferContent(vnpayToken.getVnp_txn_desc());

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_version", VnpayConfig.vnp_Version);
        vnp_Params.put("vnp_command", "token_pay");
        vnp_Params.put("vnp_tmn_code", VnpayConfig.vnp_TmnCode);
        vnp_Params.put("vnp_app_user_id", vnpayToken.getVnp_app_user_id());
        vnp_Params.put("vnp_token", vnpayToken.getVnp_token());
        vnp_Params.put("vnp_locale", vnpayToken.getVnp_locale());
        vnp_Params.put("vnp_txn_ref", VnpayConfig.getRandomNumber(8));
        vnp_Params.put("vnp_txn_desc", content);
        vnp_Params.put("vnp_amount", String.valueOf(Integer.parseInt(vnpayToken.getVnp_amount()) * REMOVE_DECIMAL_DIGITS));
        vnp_Params.put("vnp_curr_code", "VND");
        vnp_Params.put("vnp_return_url", VnpayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_cancel_url", VnpayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_ip_addr", "192.168.2.41");

        currentPaymentTime(vnp_Params);

        String[] queryAndHashData = buildQueryUrl(vnp_Params);
        String queryUrl = queryAndHashData[0];
        String hashData = queryAndHashData[1];

        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, hashData);
        queryUrl += "&vnp_secure_hash=" + vnp_SecureHash;

        return VnpayConfig.vnp_PaymentByToken + "?" + queryUrl;
    }

    public String removeToken(HttpServletRequest request, VnpayToken vnpayToken) throws InvalidRequestParameterException {
        String content =  paymentUtils.validateBankTransferContent(vnpayToken.getVnp_txn_desc());

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_version", VnpayConfig.vnp_Version);
        vnp_Params.put("vnp_command", "token_remove");
        vnp_Params.put("vnp_tmn_code", VnpayConfig.vnp_TmnCode);
        vnp_Params.put("vnp_app_user_id", vnpayToken.getVnp_app_user_id());
        vnp_Params.put("vnp_token", vnpayToken.getVnp_token());
        vnp_Params.put("vnp_txn_ref", VnpayConfig.getRandomNumber(8));
        vnp_Params.put("vnp_txn_desc", content);
        vnp_Params.put("vnp_ip_addr", "192.168.2.41");

        currentPaymentTime(vnp_Params);

        String[] queryAndHashData = buildQueryUrl(vnp_Params);
        String queryUrl = queryAndHashData[0];
        String hashData = queryAndHashData[1];

        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, hashData);
        queryUrl += "&vnp_secure_hash=" + vnp_SecureHash;

        return VnpayConfig.vnp_RemoveToken + "?" + queryUrl;
    }

    public ResponseEntity<?> paymentInformation(HttpServletRequest request, String vnp_OrderInfo, Integer vnp_Amount,
                                                String vnp_BankCode, Optional<String> vnp_BankTranNo,
                                                Optional<String> vnp_CardType, Optional<String> vnp_PayDate,
                                                String vnp_ResponseCode, String vnp_TransactionNo,
                                                String vnp_TransactionStatus, String vnp_TxnRef,
                                                String vnp_SecureHash1) {
        try {
            /*  IPN URL: Record payment results from VNPAY
            Implementation steps:
            Check checksum
            Find transactions (vnp_TxnRef) in the database (checkOrderId)
            Check the payment status of transactions before updating (checkOrderStatus)
            Check the amount (vnp_Amount) of transactions before updating (checkAmount)
            Update results to Database
            Return recorded results to VNPAY */

            // ex:  	PaymnentStatus = 0; pending
            //              PaymnentStatus = 1; success
            //              PaymnentStatus = 2; Faile

            //Begin process return from VNPAY
            Map<String, String> fields = new HashMap<>();
            for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements(); ) {
                String fieldName = URLEncoder.encode(params.nextElement(), StandardCharsets.US_ASCII);
                String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII);
                if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                    fields.put(fieldName, fieldValue);
                }
            }

            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHashType")) {
                fields.remove("vnp_SecureHashType");
            }
            if (fields.containsKey("vnp_SecureHash")) {
                fields.remove("vnp_SecureHash");
            }

            // Check checksum
            String signValue = VnpayConfig.hashAllFields(fields);
            if (signValue.equals(vnp_SecureHash)) {
                // vnp_TxnRef exists in your database
                boolean checkOrderId = true;

                /* vnp_Amount is valid (Check vnp_Amount VNPAY returns compared to the
                amount of the code (vnp_TxnRef) in the Your database) */
                boolean checkAmount = true;

                // PaymnentStatus = 0 (pending)
                boolean checkOrderStatus = true;


                if (checkOrderId) {
                    if (checkAmount) {
                        if (checkOrderStatus) {
                            if ("00".equals(request.getParameter("vnp_ResponseCode"))) {

                                //Here Code update PaymnentStatus = 1 into your Database
                            } else {

                                // Here Code update PaymnentStatus = 2 into your Database
                            }
                            System.out.print("{\"RspCode\":\"00\",\"Message\":\"Confirm Success\"}");
                        } else {
                            System.out.print("{\"RspCode\":\"02\",\"Message\":\"Order already confirmed\"}");
                        }
                    } else {
                        System.out.print("{\"RspCode\":\"04\",\"Message\":\"Invalid Amount\"}");
                    }
                } else {
                    System.out.print("{\"RspCode\":\"01\",\"Message\":\"Order not Found\"}");
                }
            } else {
                System.out.print("{\"RspCode\":\"97\",\"Message\":\"Invalid Checksum\"}");
            }
        } catch (Exception e) {
            System.out.print("{\"RspCode\":\"99\",\"Message\":\"Unknow error\"}");
        }

        return null;
    }

    private String[] buildQueryUrl(Map<String, String> vnp_Params) {
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));

                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));

                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        return new String[]{query.toString(), hashData.toString()};
    }

    private void currentPaymentTime(Map<String, String> vnp_Params) {
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_create_date", vnp_CreateDate);
    }

    private void paymentInterval(Map<String, String> vnp_Params) {
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, TIME_OUT);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
    }
}