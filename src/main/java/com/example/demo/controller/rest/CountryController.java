package com.example.demo.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CountryService;

@RestController
@RequestMapping("/api/country")
@CrossOrigin("*")
public class CountryController {
	@Autowired
	private CountryService countryService;

	@GetMapping({"","/"})
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(countryService.findAll());
	}
}
