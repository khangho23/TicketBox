package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.service.ShowTimeService;

@RestController
@RequestMapping("/api/showtime")
@CrossOrigin("*")
public class ShowTimeController {
	@Autowired
	private ShowTimeService showtimeService;

	@RequestMapping("/getShowTimeByDate")
	public ResponseEntity<?> findShowtimeByMovieAndDate(@RequestParam("showdate") String showdate,
			@RequestParam("movieId") String movieId, @RequestParam("page") Integer page, Pageable pageable) {
		return ResponseEntity.ok(showtimeService.findShowtimeByMovieAndDate(showdate,movieId, page, pageable));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") int id){
		return ResponseEntity.ok(showtimeService.findById(id));
	}

}
