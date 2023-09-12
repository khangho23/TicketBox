package com.example.demo.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.common.enums.RequestParameterEnum;
import com.example.demo.common.enums.RequestStatusEnum;
import com.example.demo.dao.CustomerDao;
import com.example.demo.entity.Customer;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.model.MailInfoModel;
import com.example.demo.util.FileUtils;

import jakarta.mail.MessagingException;

@Service
public class CustomerService implements BaseService<Customer, Integer> {
	private static final String PATH_STATIC = "D:\\cinema_projects\\BE_Cinema\\src\\main\\resources\\static\\avatar\\";

	@Autowired
	CustomerDao customerDao;
	@Autowired
	EmailService emailService;

	@Override
	public List<Customer> findAll() {
		return customerDao.findAll();
	}

	@Override
	public Optional<Customer> findById(Integer id) {
		return customerDao.findById(id);
	}

	public Optional<Customer> findByEmail(String email) {
		return customerDao.findByEmail(email);
	}

	public Customer Authenticator(String email, String password) throws InvalidRequestParameterException {
		Customer customer = customerDao.findByEmail(email)
				.orElseThrow(() -> new InvalidRequestParameterException(RequestParameterEnum.NOT_EXISTS));
		if (customer.getPassword().equals(password)) {
			return customer;
		} else {
			throw new InvalidRequestParameterException(RequestParameterEnum.WRONG);
		}
	}

	public RequestStatusEnum insert(Customer customer) throws InvalidRequestParameterException {
		if (customerDao.findByEmail(customer.getEmail()).isPresent()) {
			throw new InvalidRequestParameterException("Email", RequestParameterEnum.EXISTS);
		}
		return (customerDao.insert(customer) == 1 ? RequestStatusEnum.SUCCESS : RequestStatusEnum.FAILURE);
	}

	public RequestStatusEnum delete(Customer customer) {
		return (customerDao.delete(customer) == 1 ? RequestStatusEnum.SUCCESS : RequestStatusEnum.FAILURE);
	}

	public RequestStatusEnum updateToken(Customer customer) {
		return (customerDao.updateToken(customer) == 1 ? RequestStatusEnum.SUCCESS : RequestStatusEnum.FAILURE);
	}

	public RequestStatusEnum updateActive(Customer customer) {
		return (customerDao.updateActive(customer) == 1 ? RequestStatusEnum.SUCCESS : RequestStatusEnum.FAILURE);
	}

	public Optional<Customer> findByToken(String token) {
		return customerDao.findByToken(token);
	}

	public String registration(Customer customer) throws InvalidRequestParameterException {
		Optional<Customer> us = customerDao.findByEmail(customer.getEmail());
		if (us.isPresent()) {
			if (us.get().getToken() != null)
				// Exists Token
				throw new InvalidRequestParameterException(RequestParameterEnum.EXISTS);
		}
		try {
			return (emailService.sendCode(new MailInfoModel(customer.getEmail(),
					"Mã xác minh tài khoản của bạn trên Zuhot Cinema", customer)));
		} catch (MessagingException ex) {
			throw new InvalidRequestParameterException(RequestParameterEnum.WRONG);
		}
	}

	public RequestStatusEnum registrationConfirm(String OTP) throws InvalidRequestParameterException {
		Customer customer = customerDao.findByToken(OTP)
				.orElseThrow(() -> new InvalidRequestParameterException(RequestParameterEnum.NOT_EXISTS));
		if (customer.getToken().equals(OTP)) {
			customer.setActive(true);
			return (customerDao.updateActive(customer) == 1 ? RequestStatusEnum.SUCCESS : RequestStatusEnum.FAILURE);
		} else {
			throw new InvalidRequestParameterException(RequestParameterEnum.WRONG);
		}
	}

	public RequestStatusEnum updateInformation(Customer customer, MultipartFile multipartFile) throws IOException {
		BufferedImage img = null;
		File file = null;

		// Read image file from multipart file
		// Convert file -> multipart
		try {
			file = FileUtils.multipartFileToFileConverter(multipartFile);
			img = ImageIO.read(file);
		} catch (IOException e) {
			System.out.println(e);
			return RequestStatusEnum.FAILURE;
		}

		// Save image to local disk
		try {
			file = new File(PATH_STATIC + customer.getId() + ".jpg");
			ImageIO.write(img, "jpg", file);
			customer.setAvatar(customer.getId() + ".jpg");
		} catch (IOException e) {
			System.out.println(e);
			return RequestStatusEnum.FAILURE;
		}
		
		customerDao.updateInformation(customer);
		return RequestStatusEnum.SUCCESS;
	}
}
