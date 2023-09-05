package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.common.enums.RequestParameterEnum;
import com.example.demo.common.enums.RequestStatusEnum;
import com.example.demo.dao.CustomerDao;
import com.example.demo.entity.Customer;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.model.MailInfoModel;

import jakarta.mail.MessagingException;

@Service
public class CustomerService implements BaseService<Customer, Integer> {
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
				.orElseThrow(() -> new InvalidRequestParameterException("Email", RequestParameterEnum.NOT_EXISTS));
		if (customer.getPassword().equals(password)) {
			return customer;
		} else {
			throw new InvalidRequestParameterException("Password", RequestParameterEnum.WRONG);
		}
	}



	public int insert(Customer customer) throws InvalidRequestParameterException {
		if (customerDao.findByEmail(customer.getEmail()).isPresent()) {
			throw new InvalidRequestParameterException("Email", RequestParameterEnum.EXISTS);
		}
		if (customerDao.insert(customer) == 1) {
			return 1;
		}
		return 0;
	}

	public int delete(Customer customer) {
		if (customerDao.delete(customer) == 1) {
			return 1;
		}
		return 0;
	}

	public int update(Customer customer) {
		if (customerDao.update(customer) == 1) {
			return 1;
		}
		return 0;
	}

	public String registration(Customer customer) throws InvalidRequestParameterException {
		Optional<Customer> us = customerDao.findByEmail(customer.getEmail());
		if (us.isPresent()) {
			if (us.get().getToken() != null)
				// Exists Token
				throw new InvalidRequestParameterException("Token", RequestParameterEnum.EXISTS);
		}
		try {
			return (emailService.sendCode(
					new MailInfoModel(customer.getEmail(), "Mã xác minh tài khoản của bạn trên Zuhot Stores",
							customer)));
		} catch (MessagingException ex) {
			throw new InvalidRequestParameterException("Email", RequestParameterEnum.WRONG);
		}
	}

	public String registrationConfirm(String email, String OTP) throws InvalidRequestParameterException {
		Customer customer = customerDao.findByEmail(email).get();
		if (customer == null) {
			throw new InvalidRequestParameterException("Email", RequestParameterEnum.NOT_EXISTS);
		}
		if (customer.isActive()) {
			throw new InvalidRequestParameterException("Email", RequestParameterEnum.EXISTS);
		}
		if (customer.getToken().equals(OTP)) {
			customer.setActive(true);
			customerDao.update(customer);
			return "Successful registration confirmation !";
		}
		return "Registration confirmation failed, please try again!";
	}

	public List<Customer> findByActiveAndCreatedAtBefore(boolean active, LocalDateTime time) {
		return customerDao.findByActiveAndCreatedAtBefore(active, time);
	}
}
