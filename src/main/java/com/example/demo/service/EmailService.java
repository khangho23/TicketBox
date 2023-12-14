package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.demo.constant.Constants;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Staff;
import com.example.demo.model.MailInfoModel;
import com.example.demo.model.SendOrderModel;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

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
	public boolean sendOrder(MailInfoModel mail) throws MessagingException {
		try {
			// Tạo message
			MimeMessage message = sender.createMimeMessage();
			// Sử dụng Helper để thiết lập các thông tin cần thiết cho message
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setFrom(mail.getFrom());
			helper.setTo(mail.getTo());
			helper.setSubject(mail.getSubject());
			Map<String, Object> map = new HashMap<>();
			map.put("username", ((SendOrderModel) mail.getBody()).getInfo().getUsername());
			map.put("phone", ((SendOrderModel) mail.getBody()).getInfo().getPhone());
			map.put("email", ((SendOrderModel) mail.getBody()).getInfo().getEmail());
			map.put("listTicket", ((SendOrderModel) mail.getBody()).getListTicket());
			map.put("paymentMethod",((SendOrderModel) mail.getBody()).getPaymentMethod());
			map.put("bill", ((SendOrderModel) mail.getBody()).getBill());
			Context context = new Context();
			context.setVariables(map);
			String htmlBody = templateEngine.process(Constants.ORDER, context);
			helper.setText(htmlBody, true);
			// qrCode
			String qrCode = generateBase64QRCode(((SendOrderModel) mail.getBody()).getBill().getQrCode(), 150, 150);
			byte[] imageBytes = Base64.getDecoder().decode(qrCode);
			Resource imageResource = new ByteArrayResource(imageBytes);
			helper.addAttachment("QRCode.png", imageResource);
			// Gửi message đến SMTP server
			sender.send(message);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

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
		map.put("name", ((Customer) mail.getBody()).getName());
		map.put("url", Constants.URL);
		// random token
		String token = generateRandomToken();
		map.put("token", token);
		Context context = new Context();
		context.setVariables(map);
		String htmlBody = templateEngine.process(Constants.REGISTRATION, context);
		helper.setText(htmlBody, true);
		// Gửi message đến SMTP server
		sender.send(message);
		return token;
	}

	public void send(String to, String subject, Customer body) throws MessagingException {
		this.sendCode(new MailInfoModel(to, subject, body));
	}

	public void sendPassword(MailInfoModel mail) throws MessagingException {
		// Tạo message
		MimeMessage message = sender.createMimeMessage();
		// Sử dụng Helper để thiết lập các thông tin cần thiết cho message
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		helper.setFrom(mail.getFrom());
		helper.setTo(mail.getTo());
		helper.setSubject(mail.getSubject());
		Map<String, Object> map = new HashMap<>();
		map.put("password", ((Staff) mail.getBody()).getPassword());
		Context context = new Context();
		context.setVariables(map);
		String htmlBody = templateEngine.process(Constants.INSERT_STAFF, context);
		helper.setText(htmlBody, true);
		// Gửi message đến SMTP server
		sender.send(message);
	}

	/*
	 * Return token
	 */
	public String forgotPassword(MailInfoModel mail) throws MessagingException {
		// Tạo message
		MimeMessage message = sender.createMimeMessage();
		// Sử dụng Helper để thiết lập các thông tin cần thiết cho message
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		helper.setFrom(mail.getFrom());
		helper.setTo(mail.getTo());
		helper.setSubject(mail.getSubject());
		Map<String, Object> map = new HashMap<>();
		map.put("email", ((Customer) mail.getBody()).getEmail());
		map.put("url", Constants.URL);
		// random token
		String token = generateRandomToken();
		map.put("token", token);
		Context context = new Context();
		context.setVariables(map);
		String htmlBody = templateEngine.process(Constants.FORGOT_PASSWORD, context);
		helper.setText(htmlBody, true);
		// Gửi message đến SMTP server
		sender.send(message);
		return token;
	}

	public static String generateRandomToken() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		String token = "";
		for (int i = 0; i < 31; i++) {
			if (i == 5 || i == 13 || i == 17 || i == 21) {
				token += "-";
			}
			token += characters.charAt(random.nextInt(characters.length()));
		}
		return token;
	}

	public static String generateBase64QRCode(String data, int width, int height) throws Exception {
		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

		MultiFormatWriter writer = new MultiFormatWriter();
		BitMatrix matrix = writer.encode(data, BarcodeFormat.QR_CODE, width, height, hints);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(matrix, "PNG", stream);

		byte[] imageBytes = stream.toByteArray();
		return Base64.getEncoder().encodeToString(imageBytes);
	}
}
