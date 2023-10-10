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
	
	public List<Dashboard> statisticsTicketPriceByMovie() {
		return dashboardDao.statisticsTicketPriceByMovie();
	}
	
	public List<Dashboard> statisticsTicketPriceByMovie2(String movieName, int year){
		return dashboardDao.statisticsTicketPriceByMovie2(movieName, year);
	}
	
	public List<Dashboard> fillYear(){
		return dashboardDao.fillYear();
	}
}
