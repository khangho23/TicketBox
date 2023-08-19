package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.enums.RequestParameterEnum;
import com.example.demo.dao.MovieDao;
import com.example.demo.dto.MovieDto;
import com.example.demo.entity.Movie;
import com.example.demo.exception.InvalidRequestParameterException;

@Service
public class MovieService implements BaseService<Movie, String> {

	@Autowired
	private MovieDao movieDao;

	@Override
	public List<Movie> findAll() {
		// TODO Auto-generated method stub
		return movieDao.findAll();
	}

	@Override
	public Optional<Movie> findById(String id) throws InvalidRequestParameterException {
		return Optional.of(movieDao.findById(id).orElseThrow(() -> new InvalidRequestParameterException("id", RequestParameterEnum.NOT_FOUND)));
	}

	public List<Movie> findByStatus(String status) throws InvalidRequestParameterException {
		// TODO Auto-generated method stub
		List<Movie> list = movieDao.findByStatus(status);
		if(list.size() <= 0) {
			throw new InvalidRequestParameterException("status", RequestParameterEnum.NOT_FOUND);
		}
		return list;
	}
	
	public List<Movie> findMoviesNowShowing() {
		// TODO Auto-generated method stub
		return movieDao.findMoviesNowShowing();
	}
	
	public MovieDto findMovieDetailPage(String movieId) {
		// TODO Auto-generated method stub
		return movieDao.findMovieDetailPage(movieId);
	}
}
