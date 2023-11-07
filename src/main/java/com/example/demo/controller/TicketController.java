package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.admin.controller.enums.RequestParameterEnum;
import com.example.demo.service.TicketService;

@RestController
@RequestMapping("/api/ticket")
@CrossOrigin("*")
public class TicketController {
	@Autowired
	TicketService ticketService;

	@GetMapping("get-all")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(ticketService.findAll());
	}

	@GetMapping("get-customers")
	public ResponseEntity<?> findByCustomerId(@RequestParam Optional<Integer> customerId) {
		if (ticketService.findByCustomerId(customerId).isEmpty())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RequestParameterEnum.NOTHING);
		return ResponseEntity.ok(ticketService.findByCustomerId(customerId));
	}
}
