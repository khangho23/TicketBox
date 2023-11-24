package com.example.demo.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.service.DashboardService;

@RestController
@RequestMapping("/api/v2/dashboard")
@CrossOrigin("*")
public class DashboardController2 {
	@Autowired
	private DashboardService dashboardService;

	@GetMapping("/findTotalPriceTicket")
	public ResponseEntity<?> findTotalPriceTicketPerMonthOfYear(@RequestParam("year") int year,
			@RequestParam("branchName") String branchName) throws InvalidRequestParameterException {
		return ResponseEntity.ok(dashboardService.findTotalPriceTicketPerMonthOfYear(year, branchName));
	}

	@GetMapping("/statisticsTicketPriceByMovie")
	public ResponseEntity<?> statisticsTicketPriceByMovie(@RequestParam("branchName") String branchName) throws InvalidRequestParameterException {
		return ResponseEntity.ok(dashboardService.statisticsTicketPriceByMovie(branchName));
	}

	@GetMapping("/statisticsTicketPriceByMovie2")
	public ResponseEntity<?> statisticsTicketPriceByMovie2(@RequestParam("movieName") String movieName,@RequestParam("year") int year,@RequestParam("branchName") String branchName) throws InvalidRequestParameterException {
		return ResponseEntity.ok(dashboardService.statisticsTicketPriceByMovie2(movieName, year,branchName));
	}

	@GetMapping("/fillYear")
	public ResponseEntity<?> fillYear() {
		return ResponseEntity.ok(dashboardService.fillYear());
	}

	// empl
	@GetMapping("/statisticsTicketPriceByMovieForDay")
	public ResponseEntity<?> statisticsTicketPriceByMovieForDay(@RequestParam("movieName") String movieName,
			@RequestParam("date") String date, @RequestParam("branchId") String branchId) throws InvalidRequestParameterException {
		return ResponseEntity.ok(dashboardService.statisticsTicketPriceByMovieForDay(movieName, date, branchId));
	}

	@GetMapping("/statisticsTicketPriceByMovieFromDate")
	public ResponseEntity<?> statisticsTicketPriceByMovieFromDate(@RequestParam("movieName") String movieName,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestParam("branchId") String branchId)  throws InvalidRequestParameterException{
		return ResponseEntity
				.ok(dashboardService.statisticsTicketPriceByMovieFromDate(movieName, startDate, endDate, branchId));
	}

	@GetMapping("/movieOfBranch")
	public ResponseEntity<?> movieOfBranch(@RequestParam("branchId") String branchId) throws InvalidRequestParameterException {
		return ResponseEntity.ok(dashboardService.MovieOfBranch(branchId));
	}

	@GetMapping("/statisticsTotalShowtimeOfYear")
	public ResponseEntity<?> statisticsTotalShowtimeOfYear(@RequestParam("year") int year,
			@RequestParam("branchId") String branchId)  throws InvalidRequestParameterException{
		return ResponseEntity.ok(dashboardService.statisticsTotalShowtimeOfYear(year, branchId));
	}
}
