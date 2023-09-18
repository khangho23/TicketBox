package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import com.example.demo.dto.MovieDto;
import com.example.demo.dto.PosterDto;
import com.example.demo.entity.Movie;

@Dao
@ConfigAutowireable
public interface MovieDao {
	@Select
	List<Movie> findAll();

	@Select
	Optional<Movie> findById(String id);

	@Select
	List<Movie> findByStatus(String status);

	@Select
	List<Movie> findMoviesNowShowing();

	@Select
	MovieDto findMovieDetailPage(String movieid);

	@Select
	List<PosterDto> findByTypeOfMovieId(String[] typeofmovieid);
}
