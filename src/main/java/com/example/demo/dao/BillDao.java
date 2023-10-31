package com.example.demo.dao;

import com.example.demo.dto.BillDetailsDto;
import com.example.demo.dto.BillHistoryDto;
import com.example.demo.entity.Bill;
import com.example.demo.entity.Customer;
import com.example.demo.model.RateAndReviewBillModel;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

@Dao
@ConfigAutowireable
public interface BillDao {
	@Select
	List<BillHistoryDto> getBillHistory(Integer customerId);

	@Select
	BillDetailsDto getBillDetails(Integer billId, Integer customerId);
	
	@Insert
	int insert(Bill bill);

	@Update(sqlFile = true)
	int updateRateAndReview(RateAndReviewBillModel model);

	@Select
	List<Bill> findByMovie(String id);

	@Update(sqlFile = true)
	int updateExportStatus(int id, boolean exportstatus);
}
