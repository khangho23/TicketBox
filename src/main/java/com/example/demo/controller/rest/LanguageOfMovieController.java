package com.example.demo.controller.rest;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.service.LanguageOfMovieService;

@RestController
@RequestMapping("/api/languageOfMovie")
@CrossOrigin("*")
public class LanguageOfMovieController {
	@Autowired
	private LanguageOfMovieService languageOfMovieService;

	@GetMapping("/id")
	public ResponseEntity<?> findByMovieId(@RequestParam("movieId") String movieId) throws InvalidRequestParameterException {
		return ResponseEntity.ok(languageOfMovieService.findByMovieId(movieId));
	}

	@GetMapping({ "", "/" })
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(languageOfMovieService.findAll());
	}
	
	@GetMapping("/get-by-movieconfig")
	public ResponseEntity<?> findByMovieConfig(@RequestParam Optional<String> branchId) throws InvalidRequestParameterException {
		return ResponseEntity.ok(languageOfMovieService.findByMovieConfig(branchId));
	}
}
