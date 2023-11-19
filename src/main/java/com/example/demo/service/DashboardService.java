package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.admin.controller.enums.RequestParameterEnum;
import com.example.demo.dao.DashboardDao;
import com.example.demo.entity.Dashboard;
import com.example.demo.exception.InvalidRequestParameterException;

@Service
public class DashboardService {
	@Autowired
	private DashboardDao dashboardDao;

	public List<Dashboard> findTotalPriceTicketPerMonthOfYear(int year, String branchName)
			throws InvalidRequestParameterException {
		List<Dashboard> list = dashboardDao.findTotalPriceTicketPerMonthOfYear(year, branchName);
		if (list.size() <= 0) {
			throw new InvalidRequestParameterException("Thống kê", RequestParameterEnum.NOT_FOUND);
		}
		return list;
	}

	public List<Dashboard> statisticsTicketPriceByMovie(String branchName) throws InvalidRequestParameterException {
		List<Dashboard> list = dashboardDao.statisticsTicketPriceByMovie(branchName);
		if (list.size() <= 0) {
			throw new InvalidRequestParameterException("Thống kê", RequestParameterEnum.NOT_FOUND);
		}
		return list;
	}

	public List<Dashboard> statisticsTicketPriceByMovie2(String movieName, int year, String branchName)
			throws InvalidRequestParameterException {
		List<Dashboard> list = dashboardDao.statisticsTicketPriceByMovie2(movieName, year, branchName);
		if (list.size() <= 0) {
			throw new InvalidRequestParameterException("Thống kê", RequestParameterEnum.NOT_FOUND);
		}
		return list;
	}

	public List<Dashboard> fillYear() {
		return dashboardDao.fillYear();
	}

	public List<Dashboard> statisticsTicketPriceByMovieForDay(String movieName, String date, String branchId) throws InvalidRequestParameterException {
		List<Dashboard> list = dashboardDao.statisticsTicketPriceByMovieForDay(movieName, date, branchId);
		if(list.size()<=0) {
			throw new InvalidRequestParameterException("Thống kê", RequestParameterEnum.NOT_FOUND);
		}
		return list;
	}

	public List<Dashboard> statisticsTicketPriceByMovieFromDate(String movieName, String startDate, String endDate,
			String branchId) throws InvalidRequestParameterException {
		List<Dashboard> list = dashboardDao.statisticsTicketPriceByMovieFromDate(movieName, startDate, endDate, branchId);
		if(list.size()<=0) {
			throw new InvalidRequestParameterException("Thống kê", RequestParameterEnum.NOT_FOUND);
		}
		return list;
	}

	public List<Dashboard> MovieOfBranch(String branchId) throws InvalidRequestParameterException {
		List<Dashboard> list = dashboardDao.MovieOfBranch(branchId);
		if(list.size()<=0) {
			throw new InvalidRequestParameterException("Thống kê", RequestParameterEnum.NOT_FOUND);
		}
		return list;
	}

	public List<Dashboard> statisticsTotalShowtimeOfYear(int year, String branchId) throws InvalidRequestParameterException {
		List<Dashboard> list = dashboardDao.statisticsTotalShowtimeOfYear(year, branchId);
		if(list.size()<=0) {
			throw new InvalidRequestParameterException("Thống kê", RequestParameterEnum.NOT_FOUND);
		}
		return list;
	}
}
