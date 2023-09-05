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
import com.example.demo.entity.Customer;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.listener.ListenerEvent;
import com.example.demo.model.Account;
import com.example.demo.model.RegistrationConfirm;
import com.example.demo.service.CustomerService;

@RestController
@RequestMapping("/customer")
@CrossOrigin("*")
public class CustomerController {
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
	public void save(@RequestBody Customer customer) throws InvalidRequestParameterException {
		customerService.insert(customer);
	}

	@PostMapping("/login")
	public ResponseEntity<?> Login(@RequestBody Account account) throws InvalidRequestParameterException {
		Optional<Customer> customer = customerService.findByEmail(account.getEmail());
		if (customer.isPresent()) {
			if (customer.get().getPassword().equals(account.getPassword())) {
				return ResponseEntity.ok(customer);
			} else {
				throw new InvalidRequestParameterException("Password",RequestParameterEnum.WRONG);
			}
		} else {
			throw new InvalidRequestParameterException("Email",RequestParameterEnum.NOT_EXISTS);
		}
	}

	@Autowired
	ListenerEvent listenerEvent;

	@PostMapping("/registration")
	public ResponseEntity<?> registration(@RequestBody Customer user) throws InvalidRequestParameterException {
		Optional<Customer> customer = customerService.findByEmail(user.getEmail());
		if (customer.isPresent()) {
			if (customer.get().isActive()) {
				throw new InvalidRequestParameterException("Customer", RequestParameterEnum.EXISTS);
			}
			// If customer exists -> Update new Token
			customer.get().setToken(customerService.registration(user));
			if (customerService.update(customer.get()) == 1) {
				listenerEvent.checkTokenEvent(user.getEmail()); // Start countdown 5 Minute remove token
				return ResponseEntity.ok("Successful registration, please check your email to verify !");
			}
		}
		// If customer not exists -> Create New Token and Customer
		else {
			user.setToken(customerService.registration(user));
			if (customerService.insert(user) == 1) {
				listenerEvent.checkTokenEvent(user.getEmail()); // Start countdown 5 Minute remove token
				return ResponseEntity.ok("Successful registration, please check your email to verify !");
			}
		}
		return ResponseEntity.ok("Registration failed, please check and try again!");
	}

	@PostMapping("/registrationConfirm")
	public ResponseEntity<?> registrationConfirm(@RequestBody RegistrationConfirm registerConfirm)
			throws InvalidRequestParameterException {
		return ResponseEntity
				.ok(customerService.registrationConfirm(registerConfirm.getEmail(), registerConfirm.getCode()));
	}
}
