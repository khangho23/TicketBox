package com.example.demo.controller.rest;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.demo.entity.ShowTime;
import com.example.demo.exception.InvalidRequestParameterException;

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
	public ResponseEntity<?> findById(@PathVariable("id") int id) throws InvalidRequestParameterException {
		return ResponseEntity.ok(showtimeService.findById(id));
	}

	@GetMapping({ "/", "" })
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(showtimeService.findAll());
	}

	@PostMapping("/createShowTime")
	public ResponseEntity<?> createShowTime(@RequestBody ShowTime showTime)
			throws InvalidRequestParameterException {
		return ResponseEntity.ok(showtimeService.insert(showTime));
	}

	@GetMapping("/deleteShowTime/{id}")
	public ResponseEntity<?> deleteShowTime(@PathVariable("id") Integer id)
			throws InvalidRequestParameterException {
		return ResponseEntity.ok(showtimeService.delete(id));
	}

	@PostMapping("/updateShowTime")
	public ResponseEntity<?> updateShowTime(@RequestBody ShowTime showTime)
			throws InvalidRequestParameterException {
		return ResponseEntity.ok(showtimeService.update(showTime));
	}
}
