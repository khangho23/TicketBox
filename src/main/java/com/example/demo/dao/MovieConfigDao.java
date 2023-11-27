package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import com.example.demo.dto.MovieConfigDto;
import com.example.demo.entity.MovieConfig;

@Dao
@ConfigAutowireable
public interface MovieConfigDao {
	@Insert(sqlFile = true)
	int insert(MovieConfig movieConfig);
	
	@Update(sqlFile = true)
	int update(MovieConfig movieConfig);
	
	@Select()
	Optional<MovieConfig> findByMovieId(String movieId,String branchId);
	
	@Select()
	List<MovieConfig> findAllByMovieId(String movieId);
	
	@Select()
	List<MovieConfigDto> findAll();
}
