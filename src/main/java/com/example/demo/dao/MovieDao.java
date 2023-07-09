package com.example.demo.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Movie;

@Dao
@ConfigAutowireable
public interface MovieDao {
	@Select
	public List<Movie> findAll();
	
	@Select
	public Movie findById(String id);
	
}
