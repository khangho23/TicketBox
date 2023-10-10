package com.example.demo.controller.rest;

import com.example.demo.config.VnpayConfig;
import com.example.demo.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/api/vnpay")
@CrossOrigin(origins = "*")
public class VnpayController {/* To send a payment amount of 10,000 VND (ten thousand Vietnamese dong),
    the merchant needs to multiply it by 100. */
    private final int REMOVE_DECIMAL_DIGITS = 100;

    // The item code is Bill Payment. To provide more comprehensive reports for business purposes
    private final String BILL_PAYMENT = "250000";

    @Autowired
    HttpServletRequest request;

    @PostMapping("/pay")
    public Map<String, String> createPayment(@RequestParam("vnp_OrderInfo") String vnp_OrderInfo,
                                             @RequestParam("vnp_Amount") Integer amount,
                                             @RequestParam("vnp_Locale") Optional<String> language,
                                             @RequestParam("vnp_BankCode") String bankCode) {
        String vnp_TxnRef = VnpayConfig.getRandomNumber(8);
        String vnp_IpAddr = VnpayConfig.getIpAddress(request);
        String vnp_TmnCode = VnpayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VnpayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VnpayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * REMOVE_DECIMAL_DIGITS));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", StringUtils.convertVietnameseToNoDiacritics(vnp_OrderInfo));
        vnp_Params.put("vnp_OrderType", BILL_PAYMENT);
        vnp_Params.put("vnp_Locale", language.orElse("vn"));
        vnp_Params.put("vnp_ReturnUrl", VnpayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // Build data to hash and querystring
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));

                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));

                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String queryUrl = query.toString();
        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VnpayConfig.vnp_PayUrl + "?" + queryUrl;
        vnp_Params.put("redirect_url", paymentUrl);

        return vnp_Params;
    }


    @GetMapping(value = "/result")
    public Map<String, String> completePayment(@RequestParam(name = "vnp_OrderInfo") String vnp_OrderInfo,
                                               @RequestParam(name = "vnp_Amount") Integer vnp_Amount,
                                               @RequestParam(name = "vnp_BankCode", defaultValue = "") String vnp_BankCode,
                                               @RequestParam(name = "vnp_BankTranNo") String vnp_BankTranNo,
                                               @RequestParam(name = "vnp_CardType") String vnp_CardType,
                                               @RequestParam(name = "vnp_PayDate") String vnp_PayDate,
                                               @RequestParam(name = "vnp_ResponseCode") String vnp_ResponseCode,
                                               @RequestParam(name = "vnp_TransactionNo") String vnp_TransactionNo,
                                               @RequestParam(name = "vnp_TxnRef") String vnp_TxnRef) {
        Map<String, String> response = new HashMap<>();

        String year = vnp_PayDate.substring(0, 4);
        String month = vnp_PayDate.substring(4, 6);
        String date = vnp_PayDate.substring(6, 8);
        String hour = vnp_PayDate.substring(8, 10);
        String minutes = vnp_PayDate.substring(10, 12);
        String second = vnp_PayDate.substring(12, 14);

        String timePay = date + "-" + month + "-" + year + " " + hour + ":" + minutes + ":" + second;

        response.put("vnp_OrderInfo", vnp_OrderInfo);
        response.put("vnp_Amount", vnp_Amount.toString());
        response.put("vnp_BankCode", vnp_BankCode);
        response.put("vnp_BankTranNo", vnp_BankTranNo);
        response.put("vnp_CardType", vnp_CardType);
        response.put("vnp_PayDate", timePay);
        response.put("vnp_ResponseCode", vnp_ResponseCode);
        response.put("vnp_TransactionNo", vnp_TransactionNo);
        response.put("vnp_TxnRef", vnp_TxnRef);

        return response;
    }
}