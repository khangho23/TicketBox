package com.example.demo.dao;

import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import com.example.demo.entity.Staff;

@Dao
@ConfigAutowireable
public interface StaffDao {

	@Insert()
	int insert(Staff staff);
	
	@Select
	Optional<Staff> findByEmail(String email);

	@Select
	Optional<Staff> findById(String id);
}
