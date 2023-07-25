package com.example.demo.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

public interface BaseController {
 	public ResponseEntity<?> findAll();
 	
 	public ResponseEntity<?> findById(String id);
}
