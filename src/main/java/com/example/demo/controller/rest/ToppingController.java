package com.example.demo.controller.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.service.ToppingService;

@RestController
@RequestMapping("/api/topping")
@CrossOrigin("*")
public class ToppingController {
	@Autowired
	ToppingService toppingService;

	@GetMapping("/branch")
	public ResponseEntity<?> findByBranchId(@RequestParam("branchid") Optional<String> branchid)
			throws InvalidRequestParameterException {
		return ResponseEntity.ok(toppingService.findByBranchid(branchid));
	}
	
	@PostMapping("/order")
	public ResponseEntity<?> orderTopping(){
		return null;
	}
}
