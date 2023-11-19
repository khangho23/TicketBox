package com.example.demo.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import com.example.demo.dto.ToppingDto;
import com.example.demo.entity.ToppingOfBranch;

@Dao
@ConfigAutowireable
public interface ToppingDao {
	@Select
	ToppingOfBranch findToppingOfBranchById(Integer id);
	
	@Select
	List<ToppingDto> findByBranchId(String branchid);
	
	@Update(sqlFile = true)
	int updateToppingOfBranchAfterOrdered(Integer id, int quantity);
}
