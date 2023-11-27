package com.example.demo.controller.rest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.enums.RequestParameterEnum;
import com.example.demo.dto.requestMovieDto;
import com.example.demo.entity.Movie;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.service.MovieService;

@RestController
@RequestMapping("/api/movie")
@CrossOrigin("*")
public class MovieController {
	@Autowired
	private MovieService movieService;

	@GetMapping(value = { "", "/" })
	public ResponseEntity<?> findAll(@RequestParam("status") Optional<String> status,@RequestParam Optional<Integer> pageSize, Optional<Integer> page)
			throws InvalidRequestParameterException {
		List<Movie> list = !status.isPresent() ? movieService.findAll() : movieService.findByStatus(status.get(),pageSize,page);
		return ResponseEntity.ok(list);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable String id) throws InvalidRequestParameterException {
		return ResponseEntity.ok(movieService.findById(id).get());
	}

	@GetMapping("/nowshowing")
	public ResponseEntity<?> findMoviesNowShowing() {
		return ResponseEntity.ok(movieService.findMoviesNowShowing());
	}

	@GetMapping("/getDetail")
	public ResponseEntity<?> findMovieDetailPage(@RequestParam("id") String movieId)
			throws InvalidRequestParameterException {
		return ResponseEntity.ok(movieService.findMovieDetailPage(movieId));
	}

	@GetMapping("/findAll")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(movieService.findAll());
	}

	@GetMapping("/findMovieById")
	public ResponseEntity<?> findMovieById(@RequestParam("movieId") String movieId) {
		return ResponseEntity.ok(movieService.findMovieById(movieId));
	}

	@GetMapping("/findAllMovieAdmin")
	public ResponseEntity<?> findAllMovieAdmin() {
		return ResponseEntity.ok(movieService.findAllMovieAdmin());
	}

	@GetMapping("/getByShowTime")
	public ResponseEntity<?> getByShowTime(@RequestParam("showtimeid") int showtimeid)
			throws InvalidRequestParameterException {
		return ResponseEntity.ok(movieService.findByShowTimeId(showtimeid));
	}

	@PostMapping("/insert")
	public ResponseEntity<?> insertMovie(@RequestPart("json") requestMovieDto movie,
			@RequestPart("file") MultipartFile multipartFile)
			throws InvalidRequestParameterException, SQLException, FileNotFoundException, IOException {
		Tika tika = new Tika();
		String mimeType = tika.detect(multipartFile.getInputStream());
		if (mimeType.equals("image/png") || mimeType.equals("image/jpeg") || mimeType.equals("image/jpg")) {
			return ResponseEntity.ok(movieService.insertMovie(movie, multipartFile));
		}
		throw new InvalidRequestParameterException("Insert Failed", RequestParameterEnum.WRONG);
	}

	@PutMapping(value = "/update", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> updateMovie(@RequestPart("json") requestMovieDto movie,
			@RequestPart("file") MultipartFile multipartFile)
			throws InvalidRequestParameterException, SQLException, FileNotFoundException, IOException {
		Tika tika = new Tika();
		String mimeType = tika.detect(multipartFile.getInputStream());
		if (mimeType.equals("image/png") || mimeType.equals("image/jpeg") || mimeType.equals("image/jpg")) {
			return ResponseEntity.ok(movieService.updateMovie(movie, multipartFile));
		}
		throw new InvalidRequestParameterException("Update Failed", RequestParameterEnum.WRONG);
	}

	@GetMapping(value = "/getByBill")
	public ResponseEntity<?> getByBill(@RequestParam("id") int id) {
		return ResponseEntity.ok(movieService.getByBill(id));
	}
}
