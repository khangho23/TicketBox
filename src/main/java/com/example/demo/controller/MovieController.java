package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.MovieService;

@RestController
@RequestMapping("/movie")
@CrossOrigin("*")
public class MovieController implements BaseController{
	@Autowired
	private MovieService movieService;

	@Override
	@GetMapping(value= {"","/"})
	public ResponseEntity<?> findAll() {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(movieService.findAll());
	}
	
}
