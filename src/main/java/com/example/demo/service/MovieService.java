package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MovieDao;
import com.example.demo.entity.Movie;

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
	public Movie findById(String id) {
		// TODO Auto-generated method stub
		return movieDao.findById(id);
	}

	public List<Movie> findByStatus(String status) {
		// TODO Auto-generated method stub
		return movieDao.findByStatus(status);
	}
	
	public List<Movie> findMoviesNowShowing() {
		// TODO Auto-generated method stub
		return movieDao.findMoviesNowShowing();
	}
	
	public Movie findMovieDetailPage(String movieId) {
		// TODO Auto-generated method stub
		return movieDao.findMovieDetailPage(movieId);
	}
}
