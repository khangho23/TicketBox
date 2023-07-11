package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Movie;
import com.example.demo.service.MovieService;

@RestController
@RequestMapping("/movie")
@CrossOrigin("*")
public class MovieController{
	@Autowired
	private MovieService movieService;


	@GetMapping(value= {"","/"})
	public ResponseEntity<?> findAll() {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(movieService.findAll());
	}


	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable String id) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(movieService.findById(id));
	}

	
}
