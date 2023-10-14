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

    public VnpayPaymentDto createPayment(HttpServletRequest request, VnpayPaymentDto vnp) throws InvalidRequestParameterException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        vnp.setVnp_OrderInfo(paymentUtils.validateBankTransferContent(vnp.getVnp_OrderInfo()));

        VnpayPaymentDto vnpayPaymentDto = new VnpayPaymentDto();
        vnpayPaymentDto.setVnp_Version(VnpayConfig.vnp_Version);
        vnpayPaymentDto.setVnp_Command(VnpayConfig.vnp_Command);
        vnpayPaymentDto.setVnp_TmnCode(VnpayConfig.vnp_TmnCode);
        vnpayPaymentDto.setVnp_Amount(String.valueOf(Integer.parseInt(vnp.getVnp_Amount()) * REMOVE_DECIMAL_DIGITS));
        vnpayPaymentDto.setVnp_CurrCode("VND");
        vnpayPaymentDto.setVnp_BankCode(vnp.getVnp_BankCode());
        vnpayPaymentDto.setVnp_TxnRef(VnpayConfig.getRandomNumber(8));
        vnpayPaymentDto.setVnp_OrderInfo(vnp.getVnp_OrderInfo());
        vnpayPaymentDto.setVnp_OrderType(BILL_PAYMENT);
        vnpayPaymentDto.setVnp_Locale(vnp.getVnp_Locale());
        vnpayPaymentDto.setVnp_ReturnUrl(VnpayConfig.vnp_ReturnUrl);
        vnpayPaymentDto.setVnp_IpAddr(VnpayConfig.getIpAddress(request));

        paymentInterval(vnpayPaymentDto);

        Map<String, String> vnp_Params = new HashMap<>();
        // Populate vnp_Params with properties from vnpayPaymentDto
        BeanUtils.describe(vnpayPaymentDto).forEach((key, value) -> {
            if (value != null && !key.equals("class")) {
                vnp_Params.put(key, value);
            }
        });

        String[] queryAndHashData = buildQueryUrl(vnp_Params);
        String queryUrl = queryAndHashData[0];
        String hashData = queryAndHashData[1];

        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, hashData);
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;

        String paymentUrl = VnpayConfig.vnp_PayUrl + "?" + queryUrl;
        vnp_Params.put("redirect_url", paymentUrl);
        vnpayPaymentDto.setRedirect_url(paymentUrl);

        return vnpayPaymentDto;
    }

    public VnpayToken createToken(HttpServletRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        VnpayToken vnpayToken = new VnpayToken();

        vnpayToken.setVnp_version(VnpayConfig.vnp_Version);
        vnpayToken.setVnp_command("token_create");
        vnpayToken.setVnp_tmn_code(VnpayConfig.vnp_TmnCode);
        vnpayToken.setVnp_app_user_id("1");
        vnpayToken.setVnp_card_type("01");
        vnpayToken.setVnp_txn_ref(VnpayConfig.getRandomNumber(8));
        vnpayToken.setVnp_txn_desc("Chuyen tien");
        vnpayToken.setVnp_amount(String.valueOf(100000000));
        vnpayToken.setVnp_curr_code("VND");
        vnpayToken.setVnp_return_url(VnpayConfig.vnp_ReturnUrl);
        vnpayToken.setVnp_ip_addr("192.168.2.41");

        currentPaymentTime(vnpayToken);

        Map<String, String> vnp_Params = new HashMap<>();
        BeanUtils.describe(vnpayToken).forEach((key, value) -> {
            if (value != null && !key.equals("class")) {
                vnp_Params.put(key, value);
            }
        });

        String[] queryAndHashData = buildQueryUrl(vnp_Params);
        String queryUrl = queryAndHashData[0];
        String hashData = queryAndHashData[1];

        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, hashData);
        queryUrl += "&vnp_secure_hash=" + vnp_SecureHash;

        String paymentUrl = VnpayConfig.vnp_CreateToken + "?" + queryUrl;
        vnp_Params.put("redirect_url", paymentUrl);
        vnpayToken.setRedirect_url(paymentUrl);

        return vnpayToken;
    }

//    public VnpayToken getToken(HttpServletRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
//        VnpayToken vnpayToken = new VnpayToken();
//
//        vnpayToken.setVnp_version(VnpayConfig.vnp_Version);
//        vnpayToken.setVnp_command("token_create");
//        vnpayToken.setVnp_tmn_code(VnpayConfig.vnp_TmnCode);
//        vnpayToken.setVnp_app_user_id("1");
//        vnpayToken.setVnp_card_type("01");
//        vnpayToken.setVnp_txn_ref(VnpayConfig.getRandomNumber(8));
//        vnpayToken.setVnp_txn_desc("Chuyen tien");
//        vnpayToken.setVnp_amount(String.valueOf(100000000));
//        vnpayToken.setVnp_curr_code("VND");
//        vnpayToken.setVnp_return_url(VnpayConfig.vnp_ReturnUrl);
//        vnpayToken.setVnp_ip_addr(VnpayConfig.getIpAddress(request));
//
//        return vnpayToken;
//    }

    public VnpayToken paymentAndCreateToken(HttpServletRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        VnpayToken vnpayToken = new VnpayToken();

        vnpayToken.setVnp_version(VnpayConfig.vnp_Version);
        vnpayToken.setVnp_command("pay_and_create");
        vnpayToken.setVnp_tmn_code(VnpayConfig.vnp_TmnCode);
        vnpayToken.setVnp_app_user_id("1");
        vnpayToken.setVnp_card_type("01");
        vnpayToken.setVnp_txn_ref(VnpayConfig.getRandomNumber(8));
        vnpayToken.setVnp_txn_desc("Chuyen tien");
        vnpayToken.setVnp_amount(String.valueOf(100000000));
        vnpayToken.setVnp_curr_code("VND");
        vnpayToken.setVnp_return_url(VnpayConfig.vnp_ReturnUrl);
        vnpayToken.setVnp_ip_addr(VnpayConfig.getIpAddress(request));

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnpayToken.setVnp_create_date(vnp_CreateDate);

        Map<String, String> vnp_Params = new HashMap<>();
        BeanUtils.describe(vnpayToken).forEach((key, value) -> {
            if (value != null && !key.equals("class")) {
                vnp_Params.put(key, value);
            }
        });

        String[] queryAndHashData = buildQueryUrl(vnp_Params);
        String queryUrl = queryAndHashData[0];
        String hashData = queryAndHashData[1];

        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, hashData);
        queryUrl += "&vnp_secure_hash=" + vnp_SecureHash;

        String paymentUrl = VnpayConfig.vnp_PaymentAndCreateToken + "?" + queryUrl;
        vnp_Params.put("redirect_url", paymentUrl);
        vnpayToken.setRedirect_url(paymentUrl);

        return vnpayToken;
    }

    public VnpayToken paymentByToken(HttpServletRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        VnpayToken vnpayToken = new VnpayToken();

        vnpayToken.setVnp_version(VnpayConfig.vnp_Version);
        vnpayToken.setVnp_command("token_pay");
        vnpayToken.setVnp_tmn_code(VnpayConfig.vnp_TmnCode);
        vnpayToken.setVnp_app_user_id("1");
        vnpayToken.setVnp_card_type("01");
        vnpayToken.setVnp_txn_ref(VnpayConfig.getRandomNumber(8));
        vnpayToken.setVnp_txn_desc("Chuyen tien");
        vnpayToken.setVnp_amount(String.valueOf(100000000));
        vnpayToken.setVnp_curr_code("VND");
        vnpayToken.setVnp_return_url(VnpayConfig.vnp_ReturnUrl);
        vnpayToken.setVnp_ip_addr(VnpayConfig.getIpAddress(request));

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnpayToken.setVnp_create_date(vnp_CreateDate);
        cld.add(Calendar.MINUTE, TIME_OUT);

        Map<String, String> vnp_Params = new HashMap<>();
        BeanUtils.describe(vnpayToken).forEach((key, value) -> {
            if (value != null && !key.equals("class")) {
                vnp_Params.put(key, value);
            }
        });

        String[] queryAndHashData = buildQueryUrl(vnp_Params);
        String queryUrl = queryAndHashData[0];
        String hashData = queryAndHashData[1];

        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, hashData);
        queryUrl += "&vnp_secure_hash=" + vnp_SecureHash;

        String paymentUrl = VnpayConfig.vnp_PaymentByToken + "?" + queryUrl;
        vnp_Params.put("redirect_url", paymentUrl);
        vnpayToken.setRedirect_url(paymentUrl);

        return null;
    }

    public String removeToken(HttpServletRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        VnpayToken vnpayToken = new VnpayToken();

        vnpayToken.setVnp_version(VnpayConfig.vnp_Version);
        vnpayToken.setVnp_command("token_remove");
        vnpayToken.setVnp_tmn_code(VnpayConfig.vnp_TmnCode);
        vnpayToken.setVnp_app_user_id("1");
        vnpayToken.setVnp_txn_ref(VnpayConfig.getRandomNumber(8));
        vnpayToken.setVnp_token("token0111");
        vnpayToken.setVnp_txn_desc("Xoa token");
        vnpayToken.setVnp_ip_addr(VnpayConfig.getIpAddress(request));

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnpayToken.setVnp_create_date(vnp_CreateDate);
        cld.add(Calendar.MINUTE, TIME_OUT);

        Map<String, String> vnp_Params = new HashMap<>();
        BeanUtils.describe(vnpayToken).forEach((key, value) -> {
            if (value != null && !key.equals("class")) {
                vnp_Params.put(key, value);
            }
        });

        String[] queryAndHashData = buildQueryUrl(vnp_Params);
        String hashData = queryAndHashData[1];

        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, hashData);
        vnpayToken.setVnp_secure_hash(vnp_SecureHash);

        return RequestStatusEnum.SUCCESS.getResponse();
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

    private void currentPaymentTime(VnpayToken vnpayToken) {
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnpayToken.setVnp_create_date(vnp_CreateDate);
    }

    private void paymentInterval(VnpayPaymentDto vnpayPaymentDto) {
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnpayPaymentDto.setVnp_CreateDate(vnp_CreateDate);
        cld.add(Calendar.MINUTE, TIME_OUT);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnpayPaymentDto.setVnp_ExpireDate(vnp_ExpireDate);
    }
}