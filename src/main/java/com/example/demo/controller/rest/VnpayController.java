package com.example.demo.controller.rest;

import com.example.demo.config.VnpayConfig;
import com.example.demo.dto.VnpayPaymentDto;
import com.example.demo.dto.VnpayResultDto;
import com.example.demo.dto.VnpayToken;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.service.VnpayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/vnpay")
@CrossOrigin(origins = "*")
public class VnpayController {
    @Autowired
    HttpServletRequest request;

    @Autowired
    VnpayService vnpayService;

    @PostMapping("/pay")
    public ResponseEntity<?> createPayment(@RequestBody VnpayPaymentDto vnp) throws InvalidRequestParameterException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return ResponseEntity.ok(vnpayService.createPayment(request, vnp));
    }

    @PostMapping("/payment-information")
    public ResponseEntity<?> paymentInformation() throws InvalidRequestParameterException {
        return ResponseEntity.ok(vnpayService.paymentInformation(request));
    }

    @PostMapping("/refund")
    public ResponseEntity<?> refund(@RequestBody VnpayPaymentDto vnpayPaymentDto) throws InvalidRequestParameterException {
        return ResponseEntity.ok(vnpayService.refund(request, vnpayPaymentDto));
    }

    @PostMapping("/create-token")
    public ResponseEntity<?> createToken(@RequestBody VnpayToken vnpayToken) throws InvalidRequestParameterException {
        return ResponseEntity.ok(vnpayService.createToken(request, vnpayToken));
    }

    @PostMapping("/pay-and-create-token")
    public ResponseEntity<?> paymentAndCreateToken(@RequestBody VnpayToken vnpayToken) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InvalidRequestParameterException {
        return ResponseEntity.ok(vnpayService.paymentAndCreateToken(request, vnpayToken));
    }

    @PostMapping("/pay-by-token")
    public ResponseEntity<?> paymentByToken(@RequestBody VnpayToken vnpayToken) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InvalidRequestParameterException {
        return ResponseEntity.ok(vnpayService.paymentByToken(request, vnpayToken));
    }

    @DeleteMapping("/remove-token")
    public ResponseEntity<?> removeToken(@RequestBody VnpayToken vnpayToken) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InvalidRequestParameterException {
        return ResponseEntity.ok(vnpayService.removeToken(request, vnpayToken));
    }

    @PostMapping("/check-token")
    public ResponseEntity<?> tokenCreated() throws InvalidRequestParameterException {
        return ResponseEntity.ok(vnpayService.tokenCreated(request));
    }

    @PostMapping("/check-payment-and-token")
    public ResponseEntity<?> paymentAndTokenCreated() throws InvalidRequestParameterException {
        return ResponseEntity.ok(vnpayService.paymentAndTokenCreated(request));
    }

    @PostMapping("/payment-by-token-stage")
    public ResponseEntity<?> paymentByTokenStage() throws InvalidRequestParameterException {
        return ResponseEntity.ok(vnpayService.paymentByTokenStage(request));
    }

    @PostMapping("/check-token-remove")
    public ResponseEntity<?> removedToken(@RequestParam String vnp_response_code,
                                          @RequestParam String vnp_message)
            throws InvalidRequestParameterException {
        return ResponseEntity.ok(vnpayService.removedToken(vnp_response_code, vnp_message));
    }
}