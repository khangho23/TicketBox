package com.example.demo.dao;

import java.util.List;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import com.example.demo.dto.ShowTimeDto;

@Dao
@ConfigAutowireable
public interface ShowTimeDao {
	 @Select
	 List<ShowTimeDto> findShowTimeByMovieAndDate(String showdate, String movieId);

	 @Select
	 ShowTimeDto findById(int id);
	 
}
