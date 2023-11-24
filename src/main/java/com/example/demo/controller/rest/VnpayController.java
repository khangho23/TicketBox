package com.example.demo.controller.rest;

import com.example.demo.dto.VnpayPaymentDto;
import com.example.demo.dto.VnpayToken;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.service.VnpayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/vnpay")
@CrossOrigin(origins = "*")
public class VnpayController {
    @Autowired
    HttpServletRequest request;

    @Autowired
    VnpayService vnpayService;

    @PostMapping("/pay")
    public ResponseEntity<?> createPayment(
    		@RequestHeader("X-FORWARDED-FOR") Optional<String> ipAddress,
    		@RequestBody VnpayPaymentDto vnp,
    		@RequestParam Optional<String> billId,
    		@RequestParam Optional<String> paymentMethod)
            throws InvalidRequestParameterException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
    	return ResponseEntity
        		.ok(vnpayService.createPayment(ipAddress.orElse(request.getRemoteAddr()), vnp, billId, paymentMethod));
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
    public ResponseEntity<?> createToken(
    		@RequestHeader("X-FORWARDED-FOR") Optional<String> ipAddress,
    		@RequestBody VnpayToken vnpayToken) throws InvalidRequestParameterException {
        return ResponseEntity.ok(vnpayService.createToken(ipAddress.orElse(request.getRemoteAddr()), vnpayToken));
    }

    @PostMapping("/pay-and-create-token")
    public ResponseEntity<?> paymentAndCreateToken(
    		@RequestHeader("X-FORWARDED-FOR") Optional<String> ipAddress,
    		@RequestBody VnpayToken vnpayToken,
    		@RequestParam Optional<String>  billId,
    		@RequestParam Optional<String>  paymentMethod)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InvalidRequestParameterException {
        return ResponseEntity.ok(vnpayService.paymentAndCreateToken(ipAddress.orElse(request.getRemoteAddr()), 
        		vnpayToken, billId, paymentMethod));
    }

    @PostMapping("/pay-by-token")
    public ResponseEntity<?> paymentByToken(
    		@RequestHeader("X-FORWARDED-FOR") Optional<String> ipAddress,
    		@RequestBody VnpayToken vnpayToken,
    		@RequestParam Optional<String>  billId,
    		@RequestParam Optional<String>  paymentMethod) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InvalidRequestParameterException {
        return ResponseEntity.ok(vnpayService.paymentByToken(ipAddress.orElse(request.getRemoteAddr()), vnpayToken, billId, paymentMethod));
    }

    @DeleteMapping("/remove-token")
    public ResponseEntity<?> removeToken(
    		@RequestHeader("X-FORWARDED-FOR") Optional<String> ipAddress,
    		@RequestBody VnpayToken vnpayToken) throws InvalidRequestParameterException {
        return ResponseEntity.ok(vnpayService.removeToken(ipAddress.orElse(request.getRemoteAddr()), vnpayToken));
    }

    @GetMapping("/save-token")
    public ResponseEntity<?> saveToken() throws InvalidRequestParameterException {
        return ResponseEntity.ok(vnpayService.saveToken(request));
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
                                          @RequestParam String vnp_message,
                                          @RequestParam Optional<Integer> id)
            throws InvalidRequestParameterException {
        return ResponseEntity.ok(vnpayService.removedToken(id,vnp_response_code, vnp_message));
    }
}