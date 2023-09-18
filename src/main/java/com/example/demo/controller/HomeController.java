package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
@CrossOrigin("*")
public class HomeController {
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAll(){
		Map<String,Object> map = new HashMap<>();
		return ResponseEntity.ok(map);
	}
	
}
