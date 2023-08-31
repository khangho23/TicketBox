package com.example.demo.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MovieDao;
import com.example.demo.dao.ShowtimeDao;
import com.example.demo.dto.ShowtimeDto;
import com.example.demo.entity.Showtime;
@Service
public class ShowtimeService {
	@Autowired
	private ShowtimeDao showtimeDao;
	
	public List<ShowtimeDto> findShowtimeByMovieAndDate(LocalDate showdate, String movieId){
		return showtimeDao.findShowtimeByMovieAndDate(showdate,movieId);
	}
}
