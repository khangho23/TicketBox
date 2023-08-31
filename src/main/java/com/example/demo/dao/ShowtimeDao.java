package com.example.demo.dao;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import com.example.demo.dto.ShowtimeDto;
import com.example.demo.entity.Showtime;

@Dao
@ConfigAutowireable
public interface ShowtimeDao {
	 @Select
	 List<ShowtimeDto> findShowtimeByMovieAndDate(LocalDate showdate, String movieId);
}
