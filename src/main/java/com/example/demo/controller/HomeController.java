package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.demo.entity.Movie;
import com.example.demo.exception.InvalidRequestParameterException;
import org.seasar.doma.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.BranchService;
import com.example.demo.service.CountryService;
import com.example.demo.service.MovieService;
import com.example.demo.service.TypeOfMovieService;

@RestController
@RequestMapping("/api/home")
@CrossOrigin("*")
public class HomeController {
	@Autowired
	TypeOfMovieService typeOfMovieService;
	@Autowired
	CountryService countryService;
	@Autowired
	BranchService branchService;
	@Autowired
	MovieService movieService;

	@GetMapping("/getData")
	public ResponseEntity<?> getAll() {
		Map<String, Object> map = new HashMap<>();
		map.put("movieType", typeOfMovieService.findAll());
		map.put("country", countryService.findAll());
		map.put("branch", branchService.findAll());
		return ResponseEntity.ok(map);
	}

	@GetMapping("/search")
	public ResponseEntity<?> searchMovie(@RequestParam("country") int country,
			@RequestParam("movieType") String movieType, @RequestParam("branch") String branch,
			@RequestParam("status") String status,@RequestParam("name") String name) throws InvalidRequestParameterException {
		return ResponseEntity.ok(name.isEmpty()? movieService.findMovieHomePage(branch, country, movieType, status) : movieService.findByName(name));
	}
}
