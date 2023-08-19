package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.enums.RequestParameterEnum;
import com.example.demo.constant.Constants;
import com.example.demo.entity.Customer;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.service.CustomerService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/customer")
@CrossOrigin("*")
public class CustomerController {
	@Autowired
	HttpSession session;
	@Autowired
	CustomerService customerService;

	@GetMapping("/getAll")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(customerService.findAll());
	}

	@GetMapping("/getById")
	public ResponseEntity<?> findById(String id) {
		return ResponseEntity.ok(customerService.findById(Integer.parseInt(id)).get());
	}

	@PostMapping("/save")
	public void save(@RequestBody Customer customer) {
		customerService.insert(customer);
	}

	@GetMapping("/login")
	public ResponseEntity<?> findByKey(@RequestParam("email") String email, @RequestParam("password") String password) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(customerService.findByKey(email, password));
	}

	@PostMapping("/registration")
	public ResponseEntity<?> registration(@RequestBody Customer customer) throws InvalidRequestParameterException {
		Optional<Customer> ct = customerService.findByEmail(customer.getEmail());
		if (ct.isPresent()) {
			throw new InvalidRequestParameterException("Email", RequestParameterEnum.EXISTS);
		} else {
			try {
				String code = customerService.sendCode(customer);
				session.setAttribute("OTP", code);
				session.setAttribute("customer", customer);
				session.setMaxInactiveInterval(Constants.MAX_SESSION);
			} catch (MessagingException e) {
				throw new InvalidRequestParameterException("Email", RequestParameterEnum.WRONG);
			}
		}
		return ResponseEntity.ok("Successful registration, please check your email to verify !");
	}

	@GetMapping("/registrationConfirm")
	public ResponseEntity<?> registrationConfirm(@RequestParam("OTP") String code) {
		String OTP = (String) session.getAttribute("OTP");
		Customer ct = (Customer) session.getAttribute("customer");
		if (code.equals(OTP) || ct != null) {
			customerService.insert(ct);
			return ResponseEntity.ok("Successful registration confirmation !");
		}
		return ResponseEntity.ok("Registration confirmation failed !");
	}
}
