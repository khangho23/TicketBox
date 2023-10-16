package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.LanguageOfMovieDao;
import com.example.demo.entity.LanguageOfMovie;

@Service
public class LanguageOfMovieService {
	@Autowired
	private LanguageOfMovieDao languageOfMovieDao;
	
	public List<LanguageOfMovie> findByMovieId(String MovieId){
		return languageOfMovieDao.findByMovieId(MovieId);
	}
}
