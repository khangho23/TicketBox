package com.example.demo.controller.rest;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.PaymentDetails;
import com.example.demo.enums.BillExportStatus;
import com.example.demo.enums.RequestParameterEnum;
import com.example.demo.enums.RequestStatusEnum;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.model.PaypalModel;
import com.example.demo.service.BillService;
import com.example.demo.service.PaymentService;

@RestController
@RequestMapping("/api/paypal")
@CrossOrigin("*")
public class PaypalController {
    @Autowired
    PaymentService paymentService;
    @Autowired
    BillService billService;

    @PostMapping("/create-paymentmethoddetails")
    public ResponseEntity<?> createPaymentMethodDetails(@RequestBody PaypalModel entity)
            throws InvalidRequestParameterException {
        try {
            String qrCode = entity.getBillId() + UUID.randomUUID().toString();
            billService.updateQrCode(Optional.of(entity.getBillId()), qrCode);
            billService.updateExportStatus(Optional.of(entity.getBillId()), Optional.of(BillExportStatus.SUCCESS.getValue()));
            Optional<PaymentDetails> paymentDetails = paymentService.findByBillId(entity.getBillId());
            if (paymentDetails.isEmpty()) {
                paymentService.insertPaymentDetails(entity);
            } else {
                throw new InvalidRequestParameterException("Bill", RequestParameterEnum.EXISTS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidRequestParameterException("", RequestParameterEnum.WRONG);
        }
        return ResponseEntity.ok(RequestStatusEnum.SUCCESS.getResponse());
    }

}
