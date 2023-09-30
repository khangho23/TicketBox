package com.example.demo.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import com.example.demo.entity.DirectorOfMovie;

@Dao
@ConfigAutowireable
public interface DirectorOfMovieDao {
	@Select
	List<DirectorOfMovie> findAll();

	@Select
	List<DirectorOfMovie> findByMovieId(String movieId);
}
