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
	 List<Dashboard> findTotalPriceTicketPerMonthOfYear(int year);
	 
	 @Select
	 List<Dashboard> statisticsTicketPriceByMovie();

	 @Select
	 List<Dashboard> statisticsTicketPriceByMovie2(String movieName,int year);
}
