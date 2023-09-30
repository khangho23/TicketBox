package com.example.demo.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.MovieDetails;
import com.example.demo.entity.TypeOfMovie;

@Dao
@ConfigAutowireable
public interface MovieDetailsDao {
	@Select
	List<MovieDetails> findByMovieId(String movieId);
}
