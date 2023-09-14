package com.example.demo.controller;

import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.service.TypeOfMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movieType")
@CrossOrigin("*")
public class TypeOfMovieController {
	@Autowired
	private TypeOfMovieService typeOfMovieService;

	@GetMapping(value= {"","/"})
	public ResponseEntity<?> findAll() {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(typeOfMovieService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable String id) throws InvalidRequestParameterException{
		// TODO Auto-generated method stub
		return ResponseEntity.ok(typeOfMovieService.findById(id));
	}
	
}
