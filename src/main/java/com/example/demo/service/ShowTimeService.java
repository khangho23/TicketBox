package com.example.demo.service;

import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dao.ShowTimeDao;
import com.example.demo.dto.ShowTimeDto;
@Service
public class ShowTimeService {
	@Autowired
	private ShowTimeDao showtimeDao;
	
	public List<ShowTimeDto> findShowtimeByMovieAndDate(Date showdate, String movieId){
		return showtimeDao.findShowTimeByMovieAndDate(showdate,movieId);
	}
}
