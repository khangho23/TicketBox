package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CustomerService;

@RestController
@RequestMapping("/customer")
@CrossOrigin("*")
public class CustomerController {
	@Autowired
	CustomerService customerService;

	@GetMapping("/login")
	public ResponseEntity<?> findByKey(@RequestParam("email") String email, @RequestParam("password") String password) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(customerService.findByKey(email, password));
	}

}
