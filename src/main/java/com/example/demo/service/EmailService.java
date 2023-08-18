package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.demo.entity.Customer;
import com.example.demo.model.MailInfo;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    JavaMailSender sender;

    @Autowired
    private TemplateEngine templateEngine;

    public void send(MailInfo mail) throws MessagingException {
        // Tạo message
        MimeMessage message = sender.createMimeMessage();
        // Sử dụng Helper để thiết lập các thông tin cần thiết cho message
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setFrom(mail.getFrom());
        helper.setTo(mail.getTo());
        helper.setSubject(mail.getSubject());
        Map<String, Object> map = new HashMap<>();
        map.put("name", mail.getBody().getName());
        Context context = new Context();
        context.setVariables(map);
        String htmlBody = templateEngine.process("/util/TemplateEmail", context);
        helper.setText(htmlBody, true);
        // Gửi message đến SMTP server
        sender.send(message);
    }

    public void send(String to, String subject, Customer body) throws MessagingException {
        this.send(new MailInfo(to, subject, body));
    }

}
