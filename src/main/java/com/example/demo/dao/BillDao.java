package com.example.demo.dao;

import com.example.demo.dto.BillHistory;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

@Dao
@ConfigAutowireable
public interface BillDao {
	@Select
	List<BillHistory> getBillHistory(Integer customerId);
}
