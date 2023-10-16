package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
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
	
	@Select
	Optional<Director> findByName(String name);
	
	@Insert(exclude = {"id"})
	int insert(Director director);
}
