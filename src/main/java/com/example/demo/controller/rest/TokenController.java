package com.example.demo.controller.rest;

import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.service.TokenVnpayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/token")
@CrossOrigin(origins = "*")
public class TokenController {
    @Autowired
    TokenVnpayService tokenVnpayService;

    @GetMapping("/customer")
    public ResponseEntity<?> findByCustomerId(@RequestParam Optional<Integer> customerId) throws InvalidRequestParameterException {
    	return ResponseEntity.ok(tokenVnpayService.findByCustomerId(customerId));
    }
}