package com.example.demo.controller.rest;

import com.example.demo.config.VnpayConfig;
import com.example.demo.service.VnpayService;
import com.example.demo.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/api/vnpay")
@CrossOrigin(origins = "*")
public class VnpayController {
    @Autowired
    HttpServletRequest request;

    @Autowired
    VnpayService vnpayService;

    @PostMapping("/pay")
    public ResponseEntity<?> createPayment(@RequestParam("vnp_OrderInfo") String vnp_OrderInfo,
                                           @RequestParam("vnp_Amount") Integer amount,
                                           @RequestParam("vnp_Locale") Optional<String> language,
                                           @RequestParam("vnp_BankCode") String bankCode) {
        return ResponseEntity.ok(vnpayService.createPayment(request, vnp_OrderInfo,
                amount, language, bankCode));
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