package com.example.demo.service;

import com.example.demo.config.VnpayConfig;
import com.example.demo.model.VnpayModel;
import com.example.demo.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VnpayService {
    private final int REMOVE_DECIMAL_DIGITS = 100;
    private final String BILL_PAYMENT = "250000";
    private final int TIME_OUT = 15;

    public VnpayModel createPayment(HttpServletRequest request, String vnp_OrderInfo, Integer amount,
                                    Optional<String> language, String bankCode) {
        VnpayModel vnpayModel = new VnpayModel();
        vnpayModel.setVnp_Version(VnpayConfig.vnp_Version);
        vnpayModel.setVnp_Command(VnpayConfig.vnp_Command);
        vnpayModel.setVnp_TmnCode(VnpayConfig.vnp_TmnCode);
        vnpayModel.setVnp_Amount(String.valueOf(amount * REMOVE_DECIMAL_DIGITS));
        vnpayModel.setVnp_CurrCode("VND");
        vnpayModel.setVnp_BankCode(bankCode);
        vnpayModel.setVnp_TxnRef(VnpayConfig.getRandomNumber(8));
        vnpayModel.setVnp_OrderInfo(StringUtils.convertVietnameseToNoDiacritics(vnp_OrderInfo));
        vnpayModel.setVnp_OrderType(BILL_PAYMENT);
        vnpayModel.setVnp_Locale(language.orElse("vn"));
        vnpayModel.setVnp_ReturnUrl(VnpayConfig.vnp_ReturnUrl);
        vnpayModel.setVnp_IpAddr(VnpayConfig.getIpAddress(request));

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnpayModel.setVnp_CreateDate(vnp_CreateDate);
        cld.add(Calendar.MINUTE, TIME_OUT);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnpayModel.setVnp_ExpireDate(vnp_ExpireDate);

        Map<String, String> vnp_Params = new LinkedHashMap<>();
        // Populate vnp_Params with properties from vnpayModel
        vnp_Params.put("vnp_Version", vnpayModel.getVnp_Version());
        vnp_Params.put("vnp_Command", vnpayModel.getVnp_Command());
        vnp_Params.put("vnp_TmnCode", vnpayModel.getVnp_TmnCode());
        vnp_Params.put("vnp_Amount", vnpayModel.getVnp_Amount());
        vnp_Params.put("vnp_CurrCode", vnpayModel.getVnp_CurrCode());
        vnp_Params.put("vnp_BankCode", vnpayModel.getVnp_BankCode());
        vnp_Params.put("vnp_TxnRef", vnpayModel.getVnp_TxnRef());
        vnp_Params.put("vnp_OrderInfo", vnpayModel.getVnp_OrderInfo());
        vnp_Params.put("vnp_OrderType", vnpayModel.getVnp_OrderType());
        vnp_Params.put("vnp_Locale", vnpayModel.getVnp_Locale());
        vnp_Params.put("vnp_ReturnUrl", vnpayModel.getVnp_ReturnUrl());
        vnp_Params.put("vnp_IpAddr", vnpayModel.getVnp_IpAddr());
        vnp_Params.put("vnp_CreateDate", vnpayModel.getVnp_CreateDate());
        vnp_Params.put("vnp_ExpireDate", vnpayModel.getVnp_ExpireDate());

        String[] queryAndHashData = buildQueryUrl(vnp_Params);
        String queryUrl = queryAndHashData[0];
        String hashData = queryAndHashData[1];

        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, hashData);
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;

        String paymentUrl = VnpayConfig.vnp_PayUrl + "?" + queryUrl;
        vnp_Params.put("redirect_url", paymentUrl);
        vnpayModel.setRedirect_url(paymentUrl);

        return vnpayModel;
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
}