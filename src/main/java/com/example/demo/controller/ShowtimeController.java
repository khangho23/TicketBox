package com.example.demo.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ShowtimeDto;
import com.example.demo.entity.Showtime;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.service.ShowtimeService;

@RestController
@RequestMapping("/showtime")
@CrossOrigin("*")
public class ShowtimeController {
	@Autowired
	private ShowtimeService showtimeService;

	@RequestMapping("/movieAndDate")
	public ResponseEntity<?> findShowtimeByMovieAndDate(
			@RequestParam("showdate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate showdate,
			@RequestParam("movieId") String movieId) {
		List<ShowtimeDto> listMovieByDate = showtimeService.findShowtimeByMovieAndDate(showdate, movieId);
		return ResponseEntity.ok(listMovieByDate);
	}

}
