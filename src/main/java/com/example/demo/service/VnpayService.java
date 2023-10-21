package com.example.demo.service;

import com.example.demo.admin.controller.enums.RequestParameterEnum;
import com.example.demo.admin.controller.enums.RequestStatusEnum;
import com.example.demo.config.VnpayConfig;
import com.example.demo.dto.VnpayPaymentDto;
import com.example.demo.dto.VnpayResultDto;
import com.example.demo.dto.VnpayToken;
import com.example.demo.entity.Customer;
import com.example.demo.entity.TokenVnpay;
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
    private final int TIME_OUT = 15;

    @Autowired
    PaymentUtils paymentUtils;

    @Autowired
    BillService billService;

    @Autowired
    CustomerService customerService;

    @Autowired
    TokenVnpayService tokenVnpayService;

    public String createPayment(HttpServletRequest request, VnpayPaymentDto vnpayPaymentDto) throws InvalidRequestParameterException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String content = paymentUtils.validateBankTransferContent(vnpayPaymentDto.getVnp_OrderInfo());

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VnpayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", VnpayConfig.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(Integer.parseInt(vnpayPaymentDto.getVnp_Amount()) * REMOVE_DECIMAL_DIGITS));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", vnpayPaymentDto.getVnp_BankCode());
        vnp_Params.put("vnp_TxnRef", VnpayConfig.getRandomNumber(8));
        vnp_Params.put("vnp_OrderInfo", content);
        vnp_Params.put("vnp_OrderType", BILL_PAYMENT);
        vnp_Params.put("vnp_Locale", vnpayPaymentDto.getVnp_Locale());
        vnp_Params.put("vnp_ReturnUrl", VnpayConfig.vnp_ReturnUrlAfterComplete);
        vnp_Params.put("vnp_IpAddr", VnpayConfig.getIpAddress(request));

        paymentInterval(vnp_Params);

        String[] queryAndHashData = buildQueryUrl(vnp_Params);
        String queryUrl = queryAndHashData[0];
        String hashData = queryAndHashData[1];

        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, hashData);
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;

        return VnpayConfig.vnp_PayUrl + "?" + queryUrl;
    }

    public String refund(HttpServletRequest request, VnpayPaymentDto vnpayPaymentDto) throws InvalidRequestParameterException {
        String content = paymentUtils.validateBankTransferContent(vnpayPaymentDto.getVnp_OrderInfo());
        String fullRefund = "02";

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_RequestId", VnpayConfig.getRandomNumber(32));
        vnp_Params.put("vnp_Version", VnpayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", "refund");
        vnp_Params.put("vnp_TmnCode", VnpayConfig.vnp_TmnCode);
        vnp_Params.put("vnp_TransactionType", fullRefund);
        vnp_Params.put("vnp_TxnRef", vnpayPaymentDto.getVnp_TxnRef());
        vnp_Params.put("vnp_Amount", vnpayPaymentDto.getVnp_Amount());
        vnp_Params.put("vnp_OrderInfo", vnpayPaymentDto.getVnp_OrderInfo());
//        vnp_Params.put("vnp_TransactionNo", vnpayPaymentDto.getVnp_Locale());
        vnp_Params.put("vnp_TransactionDate", vnpayPaymentDto.getVnp_CreateDate());
        vnp_Params.put("vnp_CreateBy", "NGUYEN VAN A");
        vnp_Params.put("vnp_IpAddr", "10.68.1.113");

        paymentInterval(vnp_Params);

        String[] queryAndHashData = buildQueryUrl(vnp_Params);
        String queryUrl = queryAndHashData[0];
        String hashData = queryAndHashData[1];

        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, hashData);
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;

        return VnpayConfig.vnp_ApiUrl + "?" + queryUrl;
    }

    public String createToken(HttpServletRequest request, VnpayToken vnpayToken) throws InvalidRequestParameterException {
        String content = paymentUtils.validateBankTransferContent(vnpayToken.getVnp_txn_desc());

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
        vnp_Params.put("vnp_return_url", VnpayConfig.vnp_ReturnUrlAfterCreateToken);
        vnp_Params.put("vnp_cancel_url", VnpayConfig.vnp_ReturnUrlAfterCreateToken);
        vnp_Params.put("vnp_ip_addr", "192.168.2.41");

        currentPaymentTime(vnp_Params);

        String[] queryAndHashData = buildQueryUrl(vnp_Params);
        String queryUrl = queryAndHashData[0];
        String hashData = queryAndHashData[1];

        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, hashData);
        queryUrl += "&vnp_secure_hash=" + vnp_SecureHash;

        return VnpayConfig.vnp_CreateToken + "?" + queryUrl;
    }

    public String paymentAndCreateToken(HttpServletRequest request, VnpayToken vnpayToken) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InvalidRequestParameterException {
        String content = paymentUtils.validateBankTransferContent(vnpayToken.getVnp_txn_desc());

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
        vnp_Params.put("vnp_return_url", VnpayConfig.vnp_ReturnUrlAfterComplete);
        vnp_Params.put("vnp_cancel_url", VnpayConfig.vnp_ReturnUrlAfterComplete);
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
        String content = paymentUtils.validateBankTransferContent(vnpayToken.getVnp_txn_desc());

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
        vnp_Params.put("vnp_return_url", VnpayConfig.vnp_ReturnUrlAfterComplete);
        vnp_Params.put("vnp_cancel_url", VnpayConfig.vnp_ReturnUrlAfterComplete);
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
        String content = paymentUtils.validateBankTransferContent(vnpayToken.getVnp_txn_desc());

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

    public String paymentInformation(HttpServletRequest request) throws InvalidRequestParameterException {
        try {
            /*  IPN URL: Record payment results from VNPAY
            Implementation steps:
            Check checksum
            Find transactions (vnp_TxnRef) in the database (checkOrderId)
            Check the payment status of transactions before updating (checkOrderStatus)
            Check the amount (vnp_Amount) of transactions before updating (checkAmount)
            Update results to Database
            Return recorded results to VNPAY */

            // ex:  	    PaymnentStatus = 0; Pending
            //              PaymnentStatus = 1; Success
            //              PaymnentStatus = 2; Fail

            //Begin process return from VNPAY
            Map<String, String> fields = getFieldsFromRequest(request);

            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            fields.remove("vnp_SecureHashType");
            fields.remove("vnp_SecureHash");

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

                                return RequestStatusEnum.SUCCESS.getResponse();
                            }

                            throw new InvalidRequestParameterException("Transaction error", RequestParameterEnum.WRONG);
                        } else {
                            throw new InvalidRequestParameterException("Order already confirmed", RequestParameterEnum.WRONG);
                        }
                    } else {
                        throw new InvalidRequestParameterException("Invalid Amount", RequestParameterEnum.WRONG);
                    }
                } else {
                    throw new InvalidRequestParameterException("Order already confirmed", RequestParameterEnum.WRONG);
                }
            } else {
                throw new InvalidRequestParameterException("Invalid Checksum", RequestParameterEnum.WRONG);
            }
        } catch (Exception e) {
            throw new InvalidRequestParameterException("Unknown error", RequestParameterEnum.WRONG);
        }
    }

    public String saveToken(HttpServletRequest request) throws InvalidRequestParameterException {
        Map<String, String> fields = getFieldsFromRequest(request);

        if ("00".equals(fields.get("vnp_response_code"))) {
            TokenVnpay tokenVnpay = new TokenVnpay();
            tokenVnpay.setVnp_app_user_id(Integer.parseInt(fields.get("vnp_app_user_id")));
            tokenVnpay.setVnp_token(fields.get("vnp_token"));
            tokenVnpay.setVnp_card_number(fields.get("vnp_card_number"));

            // Insert DB
            tokenVnpayService.insert(tokenVnpay);

            return RequestStatusEnum.SUCCESS.getResponse();
        }

        throw new InvalidRequestParameterException("Token is invalid", RequestParameterEnum.WRONG);
    }

    public String paymentAndTokenCreated(HttpServletRequest request) throws InvalidRequestParameterException {
        Map<String, String> fields = getFieldsFromRequest(request);

        // Find by user id
        Customer customer = customerService.findById(Integer.valueOf(fields.get("vnp_app_user_id")))
                .orElseThrow();

        if ("00".equals(fields.get("vnp_response_code"))) {
            // Insert Token into DB
            return RequestStatusEnum.SUCCESS.getResponse();
        }

        throw new InvalidRequestParameterException("Token is invalid", RequestParameterEnum.WRONG);
    }

    public String paymentByTokenStage(HttpServletRequest request) throws InvalidRequestParameterException {
        // TODO: Check stage when payment by token
        Map<String, String> fields = getFieldsFromRequest(request);

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");
        fields.remove("vnp_SecureHash");

        if ("00".equals(fields.get("vnp_response_code"))) {
            // Insert DB
            return RequestStatusEnum.SUCCESS.getResponse();
        }

        throw new InvalidRequestParameterException("Token is invalid", RequestParameterEnum.WRONG);
    }

    public String removedToken(String vnp_response_code, String vnp_message) throws InvalidRequestParameterException {
        // TODO: Updating database
        if ("00".equals(vnp_response_code)) {
            // Update DB
            // Set token = null when Response code is 00
            return RequestStatusEnum.SUCCESS.getResponse();
        }

        throw new InvalidRequestParameterException("Remove token is failed", RequestParameterEnum.WRONG);
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

    private Map<String, String> getFieldsFromRequest(HttpServletRequest request) {
        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements(); ) {
            String fieldName = URLEncoder.encode(params.nextElement(), StandardCharsets.US_ASCII);
            String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                fields.put(fieldName, fieldValue);
            }
        }

        return fields;
    }
}