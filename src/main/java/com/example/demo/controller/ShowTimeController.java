package com.example.demo.controller;

import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

	@RequestMapping("/getShowTimeByDate")
	public ResponseEntity<?> findShowtimeByMovieAndDate(
			@RequestParam("showdate") Date showdate,
			@RequestParam("movieId") String movieId, @RequestParam("page") Integer page, Pageable pageable) {
		int startItem = page * 4;
		List<ShowTimeDto> list;

		List<ShowTimeDto> listMovieByDate = showtimeService.findShowtimeByMovieAndDate(showdate, movieId);
		if (listMovieByDate.size() <= 0) {
			return ResponseEntity.ok("Xin lỗi, không có xuất chiếu vào ngày này, hãy chọn một ngày khác.");
		} else {
			int toIndex = Math.min(startItem + 4, listMovieByDate.size());
			list = listMovieByDate.subList(startItem, toIndex);
			return ResponseEntity
					.ok(new PageImpl<>(list, PageRequest.of(pageable.getPageNumber(), 4), listMovieByDate.size()));
		}
	}
}
