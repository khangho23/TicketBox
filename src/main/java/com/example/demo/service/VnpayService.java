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
    // The item code is Bill Payment. To provide more comprehensive reports for business purposes
    private final String BILL_PAYMENT = "250000";

    // Time out payment
    private final int TIME_OUT = 15;

    public VnpayModel createPayment(HttpServletRequest request, String vnp_OrderInfo, Integer amount,
                                    Optional<String> language, String bankCode) {
        VnpayModel vnpayModel = new VnpayModel();
        vnpayModel.setVnp_Version(VnpayConfig.vnp_Version);
        vnpayModel.setVnp_Command(VnpayConfig.vnp_Command);
        vnpayModel.setVnp_TmnCode(VnpayConfig.vnp_TmnCode);
        vnpayModel.setVnp_Amount(amount);
        vnpayModel.setVnp_CurrCode("VND");
        vnpayModel.setVnp_BankCode(bankCode);
        vnpayModel.setVnp_TxnRef(VnpayConfig.getRandomNumber(8));
        vnpayModel.setVnp_OrderInfo(StringUtils.convertVietnameseToNoDiacritics(vnp_OrderInfo));
        vnpayModel.setVnp_OrderType(BILL_PAYMENT);

        vnpayModel.setVnp_Locale(language.orElse("vn"));
        vnpayModel.setVnp_ReturnUrl(VnpayConfig.vnp_ReturnUrl);
        vnpayModel.setVnp_IpAddr(VnpayConfig.getIpAddress(request));

        vnpayModel.setRedirect_url(BILL_PAYMENT);

        // Create date
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnpayModel.setVnp_CreateDate(vnp_CreateDate);
        cld.add(Calendar.MINUTE, TIME_OUT);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnpayModel.setVnp_ExpireDate(vnp_ExpireDate);

        // Build data for hash and query string
        Map<String, String> fieldValues = new LinkedHashMap<>();
        fieldValues.put("vnp_Version", vnpayModel.getVnp_Version());
        fieldValues.put("vnp_Command", vnpayModel.getVnp_Command());
        fieldValues.put("vnp_TmnCode", vnpayModel.getVnp_TmnCode());
        fieldValues.put("vnp_Amount", String.valueOf(vnpayModel.getVnp_Amount()));
        fieldValues.put("vnp_CurrCode", vnpayModel.getVnp_CurrCode());
        fieldValues.put("vnp_BankCode", vnpayModel.getVnp_BankCode());
        fieldValues.put("vnp_TxnRef", vnpayModel.getVnp_TxnRef());
        fieldValues.put("vnp_OrderInfo", vnpayModel.getVnp_OrderInfo());
        fieldValues.put("vnp_OrderType", vnpayModel.getVnp_OrderType());
        fieldValues.put("vnp_Locale", vnpayModel.getVnp_Locale());
        fieldValues.put("vnp_ReturnUrl", vnpayModel.getVnp_ReturnUrl());
        fieldValues.put("vnp_IpAddr", vnpayModel.getVnp_IpAddr());

        String queryUrl = buildQueryUrl(fieldValues);
        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, queryUrl);
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;

        String paymentUrl = VnpayConfig.vnp_PayUrl + "?" + queryUrl;
        vnpayModel.setRedirect_url(paymentUrl);

        return vnpayModel;
    }

    private String buildQueryUrl(Map<String, String> fieldValues) {
        StringBuilder query = new StringBuilder();
        fieldValues.forEach((fieldName, fieldValue) -> {
            query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
            query.append('=');
            query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
            query.append('&');
        });
        query.deleteCharAt(query.length() - 1);
        return query.toString();
    }
}
