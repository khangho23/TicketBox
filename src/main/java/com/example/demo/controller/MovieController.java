package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ResponseEntity<?> findAll(@RequestParam("status") Optional<String> status) {
		// TODO Auto-generated method stub
		List<Movie> list = !status.isPresent() ? movieService.findAll() : movieService.findByStatus(status.get());
		return ResponseEntity.ok(list);
	}


	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable String id) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(movieService.findById(id));
	}

	@GetMapping("/nowshowing")
	public ResponseEntity<?> findMoviesNowShowing() {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(movieService.findMoviesNowShowing());
	}
	
	@GetMapping("/detail/{id}")
	public ResponseEntity<?> findMovieDetailPage(@PathVariable("id") String movieId) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(movieService.findMovieDetailPage(movieId));
	}
}
