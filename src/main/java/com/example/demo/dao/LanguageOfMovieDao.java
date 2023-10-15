package com.example.demo.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import com.example.demo.dto.LanguageOfMovieDto;
import com.example.demo.entity.LanguageOfMovie;

@Dao
@ConfigAutowireable
public interface LanguageOfMovieDao {
	@Select
	List<LanguageOfMovie> findByMovieId(String movieId);

	@Select
	List<LanguageOfMovieDto> findAll();
}
