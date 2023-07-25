package com.example.demo.dao;

import com.example.demo.entity.Customer;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

import java.util.List;

@Dao
@ConfigAutowireable
public interface CustomerDao {
	@Select
	List<Customer> findAll();
	
	@Select
	Customer findById(Integer id);

	@Insert(exclude = {"id", "address", "idfb", "imagefb"})
	int insert(Customer customer);
}
