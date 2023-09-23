package com.example.demo.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.DashboardService;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin("*")
public class DashboardController {
	@Autowired
	private DashboardService dashboardService;
	
	@GetMapping("/findTotalPriceTicket")
	public ResponseEntity<?> findTotalPriceTicketPerMonthOfYear(@RequestParam("year") int year){
		return ResponseEntity.ok(dashboardService.findTotalPriceTicketPerMonthOfYear(year));
	}
	
	@GetMapping("/statisticsTicketPriceByMovie")
	public ResponseEntity<?> statisticsTicketPriceByMovie(){
		return ResponseEntity.ok(dashboardService.statisticsTicketPriceByMovie());
	}
	
	@GetMapping("/statisticsTicketPriceByMovie2")
	public ResponseEntity<?> statisticsTicketPriceByMovie2(String movieName, int year){
		return ResponseEntity.ok(dashboardService.statisticsTicketPriceByMovie2(movieName, year));
	}
}
