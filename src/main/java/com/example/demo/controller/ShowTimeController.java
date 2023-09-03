package com.example.demo.controller;

import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.ShowTimeDto;
import com.example.demo.service.ShowTimeService;

@RestController
@RequestMapping("/showtime")
@CrossOrigin("*")
public class ShowTimeController {
	@Autowired
	private ShowTimeService showtimeService;

	@RequestMapping("/movieAndDate")
	public ResponseEntity<?> findShowtimeByMovieAndDate(
			@RequestParam("showdate") Date showdate,
			@RequestParam("movieId") String movieId) {
		List<ShowTimeDto> listMovieByDate = showtimeService.findShowtimeByMovieAndDate(showdate, movieId);
		if(listMovieByDate.size()==0) {
			System.out.println("aaa");
			return ResponseEntity.ok("Xin lỗi, không có xuất chiếu vào ngày này, hãy chọn một ngày khác.");
		} else {
			return ResponseEntity.ok(listMovieByDate);
		}
	}

}
