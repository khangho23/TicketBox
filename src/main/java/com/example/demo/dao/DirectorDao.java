package com.example.demo.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import com.example.demo.entity.Director;

@Dao
@ConfigAutowireable
public interface DirectorDao {
	@Select
	List<Director> findAll();

	@Select
	Director findById(int id);
}
