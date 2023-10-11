package com.example.demo.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.LanguageService;

@RestController
@RequestMapping("/api/language")
@CrossOrigin("*")
public class LanguageController {
	@Autowired
	private LanguageService languageService;

	@GetMapping({"","/"})
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(languageService.findAll());
	}
}
