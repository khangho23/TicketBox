package com.example.demo.controller.rest;

import com.example.demo.config.VnpayConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/api/vnpay")
@CrossOrigin(origins = "*", maxAge = 3600)
public class VnpayController {
    /* To send a payment amount of 10,000 VND (ten thousand Vietnamese dong),
    the merchant needs to multiply it by 100. */
    private final Integer REMOVE_DECIMAL_PART = 100;

    @Autowired
    HttpServletRequest request;

    @PostMapping("/make")
    public Map<String, String> createPayment(@RequestParam(name = "vnp_OrderInfo") String vnp_OrderInfo,
                                             @RequestParam(name = "vnp_OrderType") String ordertype,
                                             @RequestParam(name = "vnp_Amount") Integer amount,
                                             @RequestParam(name = "vnp_Locale") String language,
                                             @RequestParam(name = "vnp_BankCode", defaultValue = "NCB") String bankcode) throws NoSuchAlgorithmException {
        String vnp_Version = VnpayConfig.vnp_Version;
        String vnp_Command = VnpayConfig.vnp_Command;
        String vnp_TxnRef = VnpayConfig.getRandomNumber(8);
        String vnp_IpAddr = VnpayConfig.getIpAddress(request);
        String vnp_TmnCode = VnpayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * REMOVE_DECIMAL_PART));
        vnp_Params.put("vnp_CurrCode", "VND");
        if (bankcode != null && bankcode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankcode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", ordertype);

        if (language != null && !language.isEmpty()) {
            vnp_Params.put("vnp_Locale", language);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", VnpayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Date dt = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(dt);
        vnp_Params.put("vnp_CreateDate", dateString);
        vnp_Params.put("vnp_TransDate", dateString);

        // Build data to hash and querystring
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
                hashData.append(fieldValue);

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

        String queryUrl = query.toString();
        String vnp_SecureHash = VnpayConfig.Sha256(VnpayConfig.vnp_HashSecret + hashData);
        queryUrl += "&vnp_SecureHashType=SHA256&vnp_SecureHash=" + vnp_SecureHash;
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
