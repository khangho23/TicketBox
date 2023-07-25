package com.example.demo.controller;

import com.example.demo.service.MovieTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movieType")
@CrossOrigin("*")
public class MovieTypeController implements BaseController{
	@Autowired
	private MovieTypeService movieTypeService;

	@Override
	@GetMapping(value= {"","/"})
	public ResponseEntity<?> findAll() {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(movieTypeService.findAll());
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable String id) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(movieTypeService.findById(id));
	}
	
}
