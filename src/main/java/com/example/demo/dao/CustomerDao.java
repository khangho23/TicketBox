package com.example.demo.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import com.example.demo.entity.Customer;

@Dao
@ConfigAutowireable
public interface CustomerDao {
	@Select
	public Customer findByKey(String email, String password);
}
