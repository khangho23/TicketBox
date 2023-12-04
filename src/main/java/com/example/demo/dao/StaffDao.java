package com.example.demo.dao;

import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import com.example.demo.dto.StaffRespDto;
import com.example.demo.entity.Staff;
import com.example.demo.model.StaffUpdatePasswordModel;

@Dao
@ConfigAutowireable
public interface StaffDao {

	@Insert()
	int insert(Staff staff);
	
	@Select
	Optional<Staff> findByEmail(String email);

	@Select
	Optional<StaffRespDto> check(String email);

	@Select
	Optional<Staff> findById(String id);

	@Update(sqlFile = true)
	int update(Staff staff);

	@Update(sqlFile = true)
	int updatePassword(StaffUpdatePasswordModel staff);
}
