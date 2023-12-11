package com.example.demo.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import com.example.demo.dto.TicketInDayDto;
import com.example.demo.entity.Dashboard;

@Dao
@ConfigAutowireable
public interface DashboardDao {

	@Select
	List<Dashboard> findTotalPriceTicketPerMonthOfYear(int year, String branchId);

	@Select
	List<Dashboard> statisticsTicketPriceByMovie(String branchId);

	@Select
	List<Dashboard> statisticsTicketPriceByMovie2(String movieId, int year, String branchId);

	@Select
	List<Dashboard> fillYear();
	
	@Select
	List<Dashboard> statisticsTicketPriceByMovieForDay(String movieId, String date, String branchId);

	@Select
	List<Dashboard> statisticsTicketPriceByMovieFromDate(String movieId, String startDate,String endDate, String branchId);
	
	@Select
	List<Dashboard> MovieOfBranch(String branchId);
	
	@Select
	List<Dashboard> statisticsTotalShowtimeOfYear(int year, String branchId);

	@Select
	List<TicketInDayDto> statisticsTotalTicketInDay(String movieId, String branchId);
}
