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
	List<Movie> findAll();
	
	@Select
	Movie findById(String id);

	@Select
	List<Movie> findByMovieType(String movieTypeId);
}
