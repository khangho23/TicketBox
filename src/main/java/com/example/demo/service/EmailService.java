package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.demo.constant.Constants;
import com.example.demo.entity.Customer;
import com.example.demo.model.MailInfoModel;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	@Autowired
	JavaMailSender sender;

	@Autowired
	private TemplateEngine templateEngine;

	/*
	 * Return token
	 */
	public String sendCode(MailInfoModel mail) throws MessagingException {
		// Tạo message
		MimeMessage message = sender.createMimeMessage();
		// Sử dụng Helper để thiết lập các thông tin cần thiết cho message
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		helper.setFrom(mail.getFrom());
		helper.setTo(mail.getTo());
		helper.setSubject(mail.getSubject());
		Map<String, Object> map = new HashMap<>();
		map.put("name", mail.getBody().getName());
		// random token
		String OTP = generateRandomToken(10);
		map.put("OTP", OTP);
		Context context = new Context();
		context.setVariables(map);
		String htmlBody = templateEngine.process(Constants.REGISTRATION, context);
		helper.setText(htmlBody, true);
		// Gửi message đến SMTP server
		sender.send(message);
		return OTP;
	}

	public void send(String to, String subject, Customer body) throws MessagingException {
		this.sendCode(new MailInfoModel(to, subject, body));
	}

	public static String generateRandomToken(int length) {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		String token = "";
		for (int i = 0; i < length; i++) {
			token += characters.charAt(random.nextInt(characters.length()));
		}
		return token;
	}
}
