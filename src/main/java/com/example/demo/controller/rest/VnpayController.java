package com.example.demo.controller.rest;

import com.example.demo.dto.VnpayPaymentDto;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.service.VnpayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> createPayment(@RequestBody VnpayPaymentDto vnp) throws InvalidRequestParameterException {
        return ResponseEntity.ok(vnpayService.createPayment(request, vnp));
    }

    @GetMapping("/payment-information")
    public Map<String, String> paymentInformation(@RequestParam("vnp_OrderInfo") String vnp_OrderInfo,
                                                  @RequestParam("vnp_Amount") Integer vnp_Amount,
                                                  @RequestParam("vnp_BankCode") String vnp_BankCode,
                                                  @RequestParam("vnp_BankTranNo") Optional<String> vnp_BankTranNo,
                                                  @RequestParam("vnp_CardType") Optional<String> vnp_CardType,
                                                  @RequestParam("vnp_PayDate") Optional<String> vnp_PayDate,
                                                  @RequestParam("vnp_ResponseCode") String vnp_ResponseCode,
                                                  @RequestParam("vnp_TransactionNo") String vnp_TransactionNo,
                                                  @RequestParam("vnp_TransactionStatus") String vnp_TransactionStatus,
                                                  @RequestParam("vnp_TxnRef") String vnp_TxnRef,
                                                  @RequestParam("vnp_SecureHash") String vnp_SecureHash) {
        Map<String, String> response = new HashMap<>();

        String payDate = vnp_PayDate.orElse("");
        String year = payDate.substring(0, 4);
        String month = payDate.substring(4, 6);
        String date = payDate.substring(6, 8);
        String hour = payDate.substring(8, 10);
        String minutes = payDate.substring(10, 12);
        String second = payDate.substring(12, 14);

        String timePay = date + "-" + month + "-" + year + " " + hour + ":" + minutes + ":" + second;

        response.put("vnp_OrderInfo", vnp_OrderInfo);
        response.put("vnp_Amount", vnp_Amount.toString());
        response.put("vnp_BankCode", vnp_BankCode);
        response.put("vnp_BankTranNo", vnp_BankTranNo.orElse(""));
        response.put("vnp_CardType", vnp_CardType.orElse(""));
        response.put("vnp_PayDate", timePay);
        response.put("vnp_ResponseCode", vnp_ResponseCode);
        response.put("vnp_TransactionNo", vnp_TransactionNo);
        response.put("vnp_TransactionStatus", vnp_TransactionStatus);
        response.put("vnp_TxnRef", vnp_TxnRef);
        response.put("vnp_SecureHash", vnp_SecureHash);

        return response;
    }
}