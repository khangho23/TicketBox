package com.example.demo.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.LanguageOfMovieService;

@RestController
@RequestMapping("/api/languageOfMovie")
@CrossOrigin("*")
public class LanguageOfMovieController {
	@Autowired
	private LanguageOfMovieService languageOfMovieService;

	@GetMapping("/id")
	public ResponseEntity<?> findByMovieId(@RequestParam("movieId") String movieId) {
		return ResponseEntity.ok(languageOfMovieService.findByMovieId(movieId));
	}
}
