package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DashboardDao;
import com.example.demo.entity.Dashboard;

@Service
public class DashboardService {
	@Autowired
	private DashboardDao dashboardDao;
	
	public List<Dashboard> findTotalPriceTicketPerMonthOfYear(int year,String branchName){
		return dashboardDao.findTotalPriceTicketPerMonthOfYear(year,branchName);
	}
	
	public List<Dashboard> statisticsTicketPriceByMovie(String branchName) {
		return dashboardDao.statisticsTicketPriceByMovie(branchName);
	}
	
	public List<Dashboard> statisticsTicketPriceByMovie2(String movieName, int year,String branchName){
		return dashboardDao.statisticsTicketPriceByMovie2(movieName, year,branchName);
	}
	
	public List<Dashboard> fillYear(){
		return dashboardDao.fillYear();
	}
	
	public List<Dashboard> statisticsTicketPriceByMovieForDay(String movieName, String date, String branchId){
		return dashboardDao.statisticsTicketPriceByMovieForDay(movieName,date,branchId);
	}
	public List<Dashboard> statisticsTicketPriceByMovieFromDate(String movieName, String startDate,String endDate, String branchId){
		return dashboardDao.statisticsTicketPriceByMovieFromDate(movieName,startDate,endDate,branchId);
	}
	public List<Dashboard> MovieOfBranch(String branchId){
		return dashboardDao.MovieOfBranch(branchId);
	}
	public List<Dashboard> statisticsTotalShowtimeOfYear(int year,String branchId){
		return dashboardDao.statisticsTotalShowtimeOfYear(year, branchId);
	}
}
