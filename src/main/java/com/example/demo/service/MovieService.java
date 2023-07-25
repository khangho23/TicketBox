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

	public List<Movie> findByMovieType(String movieTypeId) {
		// TODO Auto-generated method stub
		return movieDao.findByMovieType(movieTypeId);
	}
}
