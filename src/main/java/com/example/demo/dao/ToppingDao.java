package com.example.demo.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import com.example.demo.dto.ToppingDto;

@Dao
@ConfigAutowireable
public interface ToppingDao {
	@Select
	List<ToppingDto> findByBranchId(String branchid);
}
