package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.MailInfoModel;
import com.example.demo.model.SendOrderModel;
import com.example.demo.service.EmailService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/demo")
@CrossOrigin("*")
public class SendOrderController {
    @Autowired
    EmailService emailService;

    @PostMapping({ "", "/" })
    public ResponseEntity<?> index(@RequestBody SendOrderModel model) throws MessagingException {
        emailService.sendOrder(new MailInfoModel("anbvt562003@gmail.com",
                "Hoá Đơn tại ZuhotCinema",
                new SendOrderModel(model.getListTicket(), model.getBill(), model.getQrcode())));
        return ResponseEntity.ok("tot");
    }
}
