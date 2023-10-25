package com.example.demo.dao;

import java.util.List;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import com.example.demo.dto.ShowTimeDto;
import java.util.Optional;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Update;
import com.example.demo.entity.ShowTime;

@Dao
@ConfigAutowireable
public interface ShowTimeDao {
	@Select
	List<ShowTimeDto> findShowTimeByMovieAndDate(String showdate, String movieId);

	@Select
	Optional<ShowTimeDto> findById(int id);

	@Select
	List<ShowTimeDto> findAll();

	@Select
	List<ShowTimeDto> findByCurrentDate(String branchid);

	@Insert(exclude = { "id" })
	int insert(ShowTime showTime);

	@Update
	int update(ShowTime showTime);

	@Delete
	int delete(ShowTime showTime);
}
