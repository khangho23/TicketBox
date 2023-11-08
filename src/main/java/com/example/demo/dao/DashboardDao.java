package com.example.demo.dao;

import java.util.List;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import com.example.demo.entity.Dashboard;

@Dao
@ConfigAutowireable
public interface DashboardDao {

	@Select
	List<Dashboard> findTotalPriceTicketPerMonthOfYear(int year, String branchName);

	@Select
	List<Dashboard> statisticsTicketPriceByMovie(String branchName);

	@Select
	List<Dashboard> statisticsTicketPriceByMovie2(String movieName, int year, String branchName);

	@Select
	List<Dashboard> fillYear();
	
	@Select
	List<Dashboard> statisticsTicketPriceByMovieForDay(String movieName, String date, String branchId);

	@Select
	List<Dashboard> statisticsTicketPriceByMovieFromDate(String movieName, String startDate,String endDate, String branchId);
	
	@Select
	List<Dashboard> MovieOfBranch(String branchId);
	
	@Select
	List<Dashboard> statisticsTotalShowtimeOfYear(int year, String branchId);
}
