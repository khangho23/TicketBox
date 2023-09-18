package com.example.demo.controller.rest;

import com.example.demo.admin.controller.enums.RequestParameterEnum;
import com.example.demo.admin.controller.enums.RequestStatusEnum;
import com.example.demo.entity.Customer;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.listener.ListenerEvent;
import com.example.demo.model.AccountModel;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin("*")
public class CustomerController {
	@Autowired
	CustomerService customerService;

	@Autowired
	ListenerEvent listenerEvent;

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
	public ResponseEntity<?> Login(@RequestBody AccountModel account) throws InvalidRequestParameterException {
		return ResponseEntity.ok(customerService.Authenticator(account.getEmail(), account.getPassword()));
	}

	@PostMapping("/registration")
	public ResponseEntity<?> registration(@RequestBody Customer user) throws InvalidRequestParameterException {
		Optional<Customer> customer = customerService.findByEmail(user.getEmail());
		if (customer.isPresent()) {
			if (customer.get().isActive()) {
				throw new InvalidRequestParameterException("Email", RequestParameterEnum.EXISTS);
			}
			// If customer exists -> Update new Token
			customer.get().setToken(customerService.registration(user));
			if (customerService.updateToken(customer.get()).equals(RequestStatusEnum.SUCCESS)) {
				listenerEvent.checkTokenEvent(user.getEmail()); // Start countdown 5 Minute remove token
				return ResponseEntity.ok(RequestStatusEnum.SUCCESS.getResponse());
			}
		}
		// If customer not exists -> Create New Token and Customer
		else {
			user.setToken(customerService.registration(user));
			if (customerService.insert(user).equals(RequestStatusEnum.SUCCESS)) {
				listenerEvent.checkTokenEvent(user.getEmail()); // Start countdown 5 Minute remove token
				return ResponseEntity.ok(RequestStatusEnum.SUCCESS.getResponse());
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RequestStatusEnum.FAILURE.getResponse());
	}

	@GetMapping("/active")
	public ResponseEntity<?> registrationConfirm(@RequestParam("userToken") String token)
			throws InvalidRequestParameterException {
		return ResponseEntity.ok(customerService.registrationConfirm(token));
	}
}
