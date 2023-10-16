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
        boolean rs = emailService.sendOrder(new MailInfoModel(model.getTo(), "Hoá Đơn tại ZuhotCinema", model));
        if (rs)
            return ResponseEntity.ok("Thành công");
        return ResponseEntity.ok("Thất bại");
    }
}
